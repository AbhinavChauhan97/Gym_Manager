package com.example.gymmanager.view.dialogs

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.gymmanager.R
import com.example.gymmanager.databinding.DialogFeeBinding
import com.example.gymmanager.view.validation_notifier_edittext.ValidationNotifierEditText
import com.example.gymmanager.view.validation_notifier_edittext.ValidationNotifierEditTextGroup

class FeeDialog : DialogFragment() {

    private var doOnFeeSubmit:((String,String) -> Unit)? = null
    private var doOnDialogClose:(() -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //requireActivity().window.setBackgroundDrawableResource(R.drawable.dialog_background)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val binding = DialogFeeBinding.inflate(LayoutInflater.from(requireActivity()))
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setCustomTitle(LayoutInflater.from(context).inflate(R.layout.title_fee_dialog,null))
            .setPositiveButton("SUBMIT") { _, _ ->
                val amount = binding.amount.text.toString()
                val months = binding.months.text.toString()
                doOnFeeSubmit?.invoke(amount,months)
            }
            .create()



        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.isEnabled = false
            positiveButton.setTextColor(Color.DKGRAY)
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN)
            ValidationNotifierEditTextGroup.createGroupOf(binding.amount,binding.months)
                .setValidationListenerOnGroup(object : ValidationNotifierEditTextGroup.ValidationEditTextGroupValidationListener{
                    override fun onAllBecomeValid() {
                        positiveButton.isEnabled = true
                        positiveButton.setTextColor(Color.GREEN)
                    }

                    override fun onAnyBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText) {
                        positiveButton.isEnabled = false
                        positiveButton.setTextColor(Color.GRAY)
                    }

                })
        }
        return dialog
    }

    fun doOnDialogClosed(doOnDialogClose:() -> Unit) {
        this.doOnDialogClose = doOnDialogClose
    }
    fun doOnFeeSubmit(doOnFeeSubmit:(amount:String,months:String) -> Unit){
        this.doOnFeeSubmit = doOnFeeSubmit
    }

    override fun onDestroy() {
        super.onDestroy()
        this.doOnDialogClose?.invoke()
    }
}


