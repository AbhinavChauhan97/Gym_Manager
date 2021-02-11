package com.example.gymmanager.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.view.children
import com.example.gymmanager.R
import com.example.gymmanager.view.validation_notifier_edittext.ValidationNotifierEditText

class BorderedCardView(context: Context, attributeSet: AttributeSet? = null) : CardView(context,attributeSet),ValidationNotifierEditText.ValidationChangeListener {

    private var borderColor:Int = Color.RED
        set(value) {
        field = value
        paint.color = value
        invalidate()
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = borderColor
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }

   init {
       val ta = context.obtainStyledAttributes(attributeSet, R.styleable.BorderedCardView,0,0)
       borderColor = ta.getColor(R.styleable.BorderedCardView_borderColor,Color.RED)
       ta.recycle()
   }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRoundRect(0f,0f,width.toFloat(),height.toFloat(),radius,radius,paint)
        super.onDraw(canvas)
    }

    override fun onBecomeValid(validationNotifierEditText: ValidationNotifierEditText) {
        borderColor = Color.GREEN
    }

    override fun onBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText) {
        borderColor = Color.RED
    }

}