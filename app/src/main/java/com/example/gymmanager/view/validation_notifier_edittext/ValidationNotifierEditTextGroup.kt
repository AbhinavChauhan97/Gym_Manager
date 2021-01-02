package com.example.gymmanager.view.validation_notifier_edittext

import java.util.*

class ValidationNotifierEditTextGroup private constructor(vararg validationNotifierEditTexts: ValidationNotifierEditText) {

    private val validationNotifierEditTextList = LinkedList<ValidationNotifierEditText>()
    var validationEditTextGroupValidationListener: ValidationEditTextGroupValidationListener? = null
        private set

    interface ValidationEditTextGroupValidationListener {
        fun onAllBecomeValid()
        fun onAnyBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText)
    }

    init {
        validationNotifierEditTexts.forEach { addValidationNotifierEditText(it) }
    }

    companion object {
        @JvmStatic
        fun createGroupOf(vararg validationNotifierEditTexts: ValidationNotifierEditText) =
            ValidationNotifierEditTextGroup(*validationNotifierEditTexts)
    }

    private fun addValidationNotifierEditText(validationNotifierEditText: ValidationNotifierEditText) {
        validationNotifierEditTextList.add(validationNotifierEditText)
        validationNotifierEditText.addValidationChangeListener(object :
            ValidationNotifierEditText.ValidationChangeListener {
            override fun onBecomeValid(validationNotifierEditText: ValidationNotifierEditText) {
                for (vne in validationNotifierEditTextList) {
                    if (!vne.isValid) {
                        return
                    }
                }
                validationEditTextGroupValidationListener?.onAllBecomeValid()
            }

            override fun onBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText) {
                validationEditTextGroupValidationListener?.onAnyBecomeInvalid(
                    validationNotifierEditText
                )
            }
        })
    }

    fun setValidationListenerOnGroup(validationEditTextGroupValidationListener: ValidationEditTextGroupValidationListener): ValidationNotifierEditTextGroup {
        this.validationEditTextGroupValidationListener = validationEditTextGroupValidationListener
        return this
    }

}