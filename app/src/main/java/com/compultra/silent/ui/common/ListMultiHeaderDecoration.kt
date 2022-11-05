package com.compultra.silent.ui.common

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.compultra.silent.R
import com.compultra.silent.dpToPx


class ListMultiHeaderDecoration(context: Context?, val getHeaders: () -> Map<Int, String>) :
    RecyclerView.ItemDecoration() {

    val headerTextPaint = Paint().apply {
        if (context == null) return@apply
        style = Paint.Style.FILL
        color = Color.BLACK
        textSize = context.resources.dpToPx(18) ?: 0f
        typeface = ResourcesCompat.getFont(context, R.font.segoe_small_semibold)

    }

    val rectpos = 0
    val headerText = "Testing"
    val headerTextBaselineHeight: Int
    val headerTextBottom: Int
    val headerTextPaddingVertical = context?.resources?.dpToPx(8) ?: 0

    init {
        val rect = Rect()
        headerTextPaint.getTextBounds(headerText, 0, headerText.length, rect)
        headerTextBaselineHeight = -rect.top
        headerTextBottom = rect.bottom
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if(position in getHeaders()) {
            outRect.top = headerTextBaselineHeight + 3 * headerTextPaddingVertical
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val headers = getHeaders()
        for (view in parent.children) {
            val position = parent.getChildAdapterPosition(view)
            if (position in headers) {
//                c.drawRect(Rect(view.left, view.top - rectHeight, view.right, view.top), paint)
                c.drawText(
                    headers[position]!!,
                    view.left.toFloat(),
                    view.top - headerTextBottom.toFloat() - headerTextPaddingVertical,
                    headerTextPaint
                )


            }
        }
    }
}