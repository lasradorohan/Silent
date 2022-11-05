package com.compultra.silent.ui.common

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.compultra.silent.R
import com.compultra.silent.dpToPx

class ListGroupDecoration(context: Context, val getHeaderPos: () -> Set<Int> = { setOf() }) :
    RecyclerView.ItemDecoration() {

    val borderStrokeWidth = context.resources?.dpToPx(1) ?: 0f
    val itemBorderPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.light_stroke)
//        color = Color.BLACK
        strokeWidth = borderStrokeWidth
    }

    val cornerRadius = context.resources?.dpToPx(3) ?: 0f


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val startX = parent.paddingStart.toFloat()
        val endX = (parent.right - parent.paddingEnd).toFloat()
        val ovalLeft = RectF(startX, 0f, startX + 2 * cornerRadius, 2 * cornerRadius)
        val ovalRight = RectF(endX - 2 * cornerRadius, 0f, endX, 2 * cornerRadius)
        val topPath = Path().apply {
            moveTo(startX, cornerRadius)
            arcTo(ovalLeft, 180f, 90f)
            lineTo(endX - cornerRadius, 0f)
            arcTo(ovalRight, 270f, 90f)
        }

        val bottomPath = Path().apply {
            moveTo(startX, cornerRadius)
            arcTo(ovalLeft, 180f, -90f)
            lineTo(endX - cornerRadius, 2 * cornerRadius)
            arcTo(ovalRight, 90f, -90f)
        }

        val headerPos = getHeaderPos()

        for (view in parent.children) {
            val position = parent.getChildAdapterPosition(view)
            val lastItem =
                ((position + 1 in headerPos) || position == (parent.adapter?.itemCount ?: 0) - 1)
            val firstItem = (position == 0 || position in headerPos)

//oval.offset(view.left.toFloat(), view.top.toFloat())


            if (firstItem) {
                c.save()
                c.translate(0f, view.top.toFloat())
                c.drawPath(topPath, itemBorderPaint)
                c.restore()
            }
            if (lastItem) {
                c.save()
                c.translate(0f, view.bottom.toFloat() - 2 * cornerRadius)
                c.drawPath(bottomPath, itemBorderPaint)
                c.restore()
            } else {
                c.drawLine(
                    startX,
                    view.bottom.toFloat(),
                    endX,
                    view.bottom.toFloat(),
                    itemBorderPaint
                )
            }

            val startY = view.top + (if (firstItem) cornerRadius else 0f)
            val endY = view.bottom - (if (lastItem) cornerRadius else 0f)
            c.drawLine(startX, startY, startX, endY, itemBorderPaint)
            c.drawLine(endX, startY, endX, endY, itemBorderPaint)
            //            val path = Path()
//            val rectF = RectF(
//                view.left.toFloat(), view.top.toFloat(),
//                view.right.toFloat(), view.bottom.toFloat()
//            )
//            path.addRoundRect(rectF, corners, Path.Direction.CW)
//            c.drawPath(path, itemBorderPaint)
//        break
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == (parent.adapter?.itemCount ?: 0) - 1) outRect.bottom =
            borderStrokeWidth.toInt()
    }
}