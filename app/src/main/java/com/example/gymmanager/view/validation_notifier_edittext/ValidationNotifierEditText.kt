package com.example.gymmanager.view.validation_notifier_edittext

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.gymmanager.R
import java.util.*

class ValidationNotifierEditText(context: Context, attributeSet: AttributeSet? = null)
    : androidx.appcompat.widget.AppCompatEditText(context, attributeSet) {

    private var hasBorder:Boolean
    private var borderColor = Color.BLACK
    set(value) {
        field = value
        paint?.color = value
        invalidate()
    }
    private var cornerRadius:Float? = null
    private var borderWidth:Float? = null
    private var paint:Paint? = null
    private val validatorRegex: String?
    private val validationErrorMessage: String?
    var isValid = false
    private set
    private var validationChangeListenerList: MutableList<ValidationChangeListener> = LinkedList()
    private var previouslyMatched = false


    interface ValidationChangeListener {
        fun onBecomeValid(validationNotifierEditText: ValidationNotifierEditText)
        fun onBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText)
    }

    fun addValidationChangeListener(validationChangeListener: ValidationChangeListener) {
        validationChangeListenerList.add(validationChangeListener)
    }
    fun removeValidationChangeListener(validationChangeListener: ValidationChangeListener):Boolean{
        return validationChangeListenerList.remove(validationChangeListener)
    }


    init {
        val ta = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.ValidationNotifierEditText,
            0,
            0
        )
        validatorRegex = ta.getString(R.styleable.ValidationNotifierEditText_validatorRegex)
        validationErrorMessage =
            ta.getString(R.styleable.ValidationNotifierEditText_validationErrorMessage)
        hasBorder = ta.getBoolean(R.styleable.ValidationNotifierEditText_giveBorder,false)

        if(hasBorder){
            borderColor = ta.getColor(R.styleable.ValidationNotifierEditText_borderColour,Color.BLACK)
            cornerRadius = ta.getDimension(R.styleable.ValidationNotifierEditText_cornerRadius,0f)
            borderWidth = ta.getDimension(R.styleable.ValidationNotifierEditText_borderWidth,5f)
            paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = borderColor
                strokeWidth = borderWidth as Float
                style = Paint.Style.STROKE
            }
        }
        ta.recycle()
        if (validatorRegex != null) {
            doOnTextChanged { text, start, before, count ->
                super.onTextChanged(text, start, before, count)
                if (text != null) {
                    if (text.matches(validatorRegex.toRegex())) {
                        if (!previouslyMatched) {
                            isValid = true
                            previouslyMatched = true
                            notifyValidity()
                        }
                    } else {
                        if (previouslyMatched) {
                            isValid = false
                            previouslyMatched = false
                            notifyInvalidity()
                        }
                    }
                }
            }
        }
        else {
            isValid = true
        }
    }
    private  fun notifyValidity(){
        validationChangeListenerList.forEach { it.onBecomeValid(this) }
        borderColor = Color.GREEN
    }
    private fun notifyInvalidity(){
        validationChangeListenerList.forEach { it.onBecomeInvalid(this) }
        borderColor = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(hasBorder){
            canvas?.drawRoundRect(0f + borderWidth!!,
                0f + borderWidth!!,
                width.toFloat() - borderWidth!!
                ,height.toFloat() - borderWidth!!
                ,cornerRadius!!,cornerRadius!!,paint!!)
        }
    }

}