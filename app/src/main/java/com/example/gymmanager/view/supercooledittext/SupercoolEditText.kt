package com.example.gymmanager.view.supercooledittext

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.core.widget.doOnTextChanged
import com.example.gymmanager.R

class SupercoolEditText(context: Context,attributeSet: AttributeSet? = null) : androidx.appcompat.widget.AppCompatEditText(context,attributeSet) {

    private var validatorRegex:String
    private var validationErrorMessage:String
    var validationStateChangeListener:ValidationStateChangeListener? = null
    private var previouslyMatched = false
    interface ValidationStateChangeListener{
        fun onValidationStateChanged(valid : Boolean)
    }

    init {
        val ta = context.obtainStyledAttributes(attributeSet,R.styleable.SupercoolEditText,0,0)
        validatorRegex = ta.getString(R.styleable.SupercoolEditText_validatorRegex) ?: ".+"
        validationErrorMessage = ta.getString(R.styleable.SupercoolEditText_validationErrorMessage) ?: "invalid input"
        ta.recycle()
        setTextColor(Color.BLACK)
        doOnTextChanged { text, start, before, count ->
            super.onTextChanged(text,start,before,count)
            if(text != null){
                if(text.matches(validatorRegex.toRegex())){
                    if(!previouslyMatched) {
                        paint.color = Color.GREEN
                        validationStateChangeListener?.onValidationStateChanged(true)
                        previouslyMatched = true
                        invalidate()
                    }
                }
                else{
                    if(previouslyMatched) {
                        paint.color = Color.RED
                        validationStateChangeListener?.onValidationStateChanged(false)
                        previouslyMatched = false
                        invalidate()
                    }
                    error = validationErrorMessage
                }
            }
        }
    }
}