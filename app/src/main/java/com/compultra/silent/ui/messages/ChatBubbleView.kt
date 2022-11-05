package com.compultra.silent.ui.messages
//
//import android.content.Context
//import android.graphics.*
//import android.util.AttributeSet
//import android.util.Log
//import android.view.View
//import android.view.ViewOutlineProvider
//import android.widget.FrameLayout
//import androidx.appcompat.content.res.AppCompatResources
//import com.compultra.silent.R
//import com.compultra.silent.dpToPx
//
//class ChatBubbleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): FrameLayout(context) {
//
////    class CustomOutline: ViewOutlineProvider {
////        override fun getOutline(view: View?, outline: Outline) {
////            outline.set
////        }
////    }
//
//    var borderPath = Path()
//
//    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        super.onLayout(changed, left, top, right, bottom)
//        borderPath.apply {
//            reset()
//            moveTo(outerPadding, outerPadding)
//            lineTo(width.toFloat()-outerPadding, outerPadding)
//            lineTo(width.toFloat()-outerPadding, height.toFloat()-outerPadding)
//            lineTo(outerPadding, height.toFloat()-outerPadding)
//            lineTo(outerPadding, outerPadding)
//            close()
//
//        }
//    }
//
//    val outerPadding: Float = context.resources.dpToPx(8)
//    val innerPadding: Int = context.resources.dpToPx(10)
//    val kinkWidth: Int = context.resources.dpToPx(5)
//    val kinkHeight: Int = context.resources.dpToPx(12)
//    val tempSize: Float = context.resources.dpToPx(40)
//    init {
//
//
//
//
//    }
//
//    val noise = resources.getDrawable(R.drawable.ic_launcher_background, null)
//
//    val paint = Paint().apply {
//        style = Paint.Style.FILL
////        background =  AppCompatResources.getDrawable(context, R.drawable.ic_launcher_background)
//        strokeWidth = context.resources?.dpToPx(2) ?: 0f
//        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
////        color = Color.BLACK
//    }
//    override fun onDraw(canvas: Canvas?)  {
//        if (canvas==null) return
//        canvas.drawPath(borderPath, paint)
////        canvas.
////        canvas?.drawLine(paddingStart.toFloat(), paddingTop.toFloat(), width.toFloat(), height.toFloat(), paint)
//        noise.setBounds(0, 0, width, height)
//        noise.draw(canvas)
//        Log.d("WOHOO", "paddingStart = $paddingStart")
//        super.onDraw(canvas)
//    }
//}