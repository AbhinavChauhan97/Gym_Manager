package com.example.gymmanager.view.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SimpleConfirmationDialog private constructor(): DialogFragment(){

    private var onAccept : (() -> Unit)? = null
    private var onReject : (() -> Unit)? = null
    companion object{
        fun withText(text :String):SimpleConfirmationDialog{
            val args = Bundle()
            args.putString("text",text)
            val thisDialog = SimpleConfirmationDialog()
            thisDialog.arguments = args
            return thisDialog
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val text = arguments?.getString("text")
        return AlertDialog.Builder(requireActivity())
              .setMessage(text)
            .setPositiveButton("YES"
            ) { _, _ -> onAccept?.invoke() }
            .setNegativeButton("NO") { _, _ -> onReject?.invoke() }
            .create()
    }

    fun doOnAccepted(onAccept:(() -> Unit)){
        this.onAccept = onAccept
    }

    fun doOnRejected(onReject:(()-> Unit)){
        this.onReject = onReject
    }
}