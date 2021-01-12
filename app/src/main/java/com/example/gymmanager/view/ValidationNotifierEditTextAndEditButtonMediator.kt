package com.example.gymmanager.view

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.gymmanager.R
import com.example.gymmanager.repository.DocumentReferenceProvider
import com.example.gymmanager.util.appContext
import com.example.gymmanager.view.validation_notifier_edittext.ValidationNotifierEditText
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.lang.IllegalStateException

class ValidationNotifierEditTextAndEditButtonMediator private constructor(){
    lateinit var validationNotifierEditText: ValidationNotifierEditText
    lateinit var editButton: MemberDetailsEditButton
    lateinit var documentReferenceProvider: DocumentReferenceProvider

   /* private val editDrawable = ContextCompat.getDrawable(context,R.drawable.ic_baseline_edit_24)
    private val invalidDrawable =
        ContextCompat.getDrawable(context,R.drawable.ic_baseline_clear_24)?.apply {
            this.setTint(Color.RED) }
    private val validDrawable =
        ContextCompat.getDrawable(context,R.drawable.ic_baseline_check_24)?.apply {
        this.setTint(Color.GREEN)
    }*/
    private lateinit var originalText:String

    companion object {
        private var mediator:ValidationNotifierEditTextAndEditButtonMediator = ValidationNotifierEditTextAndEditButtonMediator()
        fun withContext(context: Context){
            mediator = ValidationNotifierEditTextAndEditButtonMediator()
        }
        fun setValidationNotifierEditText(validationNotifierEditText: ValidationNotifierEditText):Companion {
           // checkMediatorInitialized()
            mediator.validationNotifierEditText = validationNotifierEditText
            return this
        }

        fun setEditButton(editButton: MemberDetailsEditButton): Companion {
            //checkMediatorInitialized()
            mediator.editButton = editButton
            return this
        }

        fun newDataHolder(documentReferenceProvider: DocumentReferenceProvider):Companion{
            mediator.documentReferenceProvider = documentReferenceProvider
            return  this
        }

        fun setup():ValidationNotifierEditTextAndEditButtonMediator{
            mediator.startMediating()
            return mediator
        }

        private fun checkMediatorInitialized(){
           // if(!this::mediator.isInitialized){
            //    throw IllegalStateException("withContext(Context) was not called before this method")
           // }
        }

    }

    fun startMediating() {
        if(!this::validationNotifierEditText.isInitialized){
            throw IllegalStateException("an instance of ValidationNotifierEditText was not provided by calling setValidationNotifierEditText(ValidationNotifierEditText)")
        }
        if(!this::editButton.isInitialized){
            throw IllegalStateException("an instance of imageView was not provided by calling setEditButton(ImageView)")
        }
        if(!this::documentReferenceProvider.isInitialized){
            throw IllegalStateException("the reference to document which should be was not provided by calling editInDocument(DocumentReference)")
        }
        if (!validationNotifierEditText.isEnabled) {
            originalText = validationNotifierEditText.text.toString()
            validationNotifierEditText.addValidationChangeListener(object :
                ValidationNotifierEditText.ValidationChangeListener {
                override fun onBecomeValid(validationNotifierEditText: ValidationNotifierEditText) {
                    editButton.setValidDrawable()
                }

                override fun onBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText) {
                    editButton.setInvalidDrawable()
                }
            })
            editButton.setValidDrawable()
            validationNotifierEditText.isEnabled = true
            validationNotifierEditText.requestFocus()
        } else {
            if (validationNotifierEditText.isValid && validationNotifierEditText.text.toString() != originalText) {
                documentReferenceProvider.documentReference.set(documentReferenceProvider,SetOptions.merge())
            } else {
                validationNotifierEditText.setText(originalText)
            }
            editButton.setValidDrawable()
            validationNotifierEditText.isEnabled = false
        }
    }
}
