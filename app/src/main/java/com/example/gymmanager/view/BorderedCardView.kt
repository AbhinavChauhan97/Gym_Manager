package com.example.gymmanager.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class BorderedCardView(context: Context, attributeSet: AttributeSet? = null) : CardView(context,attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }

    fun setBorderColor(color:Int){
        paint.color = color
        invalidate()
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRoundRect(0f,0f,width.toFloat(),height.toFloat(),radius,radius,paint)
    }
}