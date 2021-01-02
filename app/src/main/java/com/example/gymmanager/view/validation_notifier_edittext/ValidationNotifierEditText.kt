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
        ta.recycle()
        setTextColor(Color.BLACK)
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

    }
    private fun notifyInvalidity(){
        validationChangeListenerList.forEach { it.onBecomeInvalid(this) }

    }

}