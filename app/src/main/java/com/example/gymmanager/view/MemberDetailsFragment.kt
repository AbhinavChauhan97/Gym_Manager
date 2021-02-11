package com.example.gymmanager.view

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gymmanager.R
import com.example.gymmanager.databinding.FragmentMemberDetailsBinding
import com.example.gymmanager.repository.DocumentReferenceProvider
import com.example.gymmanager.repository.updateMemberDetailsField
import com.example.gymmanager.view.dialogs.FeeDialog
import com.example.gymmanager.view.dialogs.SimpleConfirmationDialog
import com.example.gymmanager.view.validation_notifier_edittext.ValidationNotifierEditText
import com.example.gymmanager.viewmodel.MemberDetailsFragmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MemberDetailsFragment : Fragment(R.layout.fragment_member_details) {

    private val args: MemberDetailsFragmentArgs by navArgs()
    lateinit var binding: FragmentMemberDetailsBinding
    lateinit var viewModel: MemberDetailsFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMemberDetailsBinding.bind(view)
        viewModel = ViewModelProviders.of(this).get(MemberDetailsFragmentViewModel::class.java)
        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this
        viewModel.conciseMember = args.conciseMember
        viewModel.loadMember(args.conciseMember.id)
        binding.name.addValidationChangeListener(binding.nameCardview)
        binding.phone.addValidationChangeListener(binding.phoneCardview)
        binding.address.addValidationChangeListener(binding.addressCardview)
        binding.joiningDate.addValidationChangeListener(binding.dateCardview)
    }

    fun submitFees(view: View) {
        val fab = view as FloatingActionButton
        fab.isClickable = false
        FeeDialog().apply {
            doOnDialogClosed { fab.isClickable = true }
            doOnFeeSubmit { amount, months ->
                binding.feeFabProgressCircle.show()
                viewModel.viewModelScope.launch(Dispatchers.Main) {
                    val isFeeSubmitted = viewModel.submitFees(amount, months)
                    if (isFeeSubmitted) {
                        showToast("Fees Submitted")
                    } else {
                        showToast(null)
                    }
                    binding.feeFabProgressCircle.hide()
                    fab.isClickable = true
                }
            }
        }.show(requireActivity().supportFragmentManager, null)
    }

    fun deleteMember(view: View) {

        val dialog =
            SimpleConfirmationDialog.withText("Are you sure you want to delete this member");
        val fab = view as FloatingActionButton
        dialog.doOnAccepted {
            fab.isClickable = false
            binding.deleteFabProgressCircle.show()
            viewModel.viewModelScope.launch(Dispatchers.Main) {
                val isMemberDeleted = viewModel.deleteThisMember()
                if (isMemberDeleted) {
                    val action =
                        MemberDetailsFragmentDirections.actionMemberDetailsFragmentToMainFragment()
                    findNavController().navigate(action)
                } else {
                    showToast(null)
                }
            }
        }
        dialog.show(requireActivity().supportFragmentManager, null)
    }

    fun callMember() {
        val intent =
            Intent(Intent.ACTION_CALL, Uri.parse("tel:${viewModel.memberDetails.value?.phone}"))
        startActivity(intent)
    }

    private fun showToast(toastText: String?) {
        Toast.makeText(
            this@MemberDetailsFragment.requireActivity(),
            toastText ?: "something went wrong,try again",
            Toast.LENGTH_LONG
        ).show()
    }

    fun editPressed(
        validationNotifierEditText: ValidationNotifierEditText,
        view: View,
        documentReferenceProvider: DocumentReferenceProvider,
        fieldName:String
    ) {
       val editButton = view as ImageButton
       val pencilSrc = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_edit_black_20)
       val crossSrc = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_clear_red_20)
       val checkSrc = ContextCompat.getDrawable(requireActivity(),R.drawable.ic_baseline_check_green_20)
       if(validationNotifierEditText.isEnabled){
           if(validationNotifierEditText.isValid){
               updateMemberDetailsField(documentReferenceProvider.documentReference,fieldName,validationNotifierEditText.text.toString())
               validationNotifierEditText.isEnabled = false
               editButton.setImageDrawable(pencilSrc)
           }
       }else{
           validationNotifierEditText.isEnabled = true
           validationNotifierEditText.addValidationChangeListener(object : ValidationNotifierEditText.ValidationChangeListener{
               override fun onBecomeValid(validationNotifierEditText: ValidationNotifierEditText) {
                   editButton.setImageDrawable(checkSrc)
               }
               override fun onBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText) {
                   editButton.setImageDrawable(crossSrc)
               }
           })
           validationNotifierEditText.requestFocus()
           editButton.setImageDrawable(checkSrc)
       }
    }
}