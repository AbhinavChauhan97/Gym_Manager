package com.example.gymmanager.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gymmanager.R
import com.example.gymmanager.databinding.FragmentMemberDetailsBinding
import com.example.gymmanager.repository.DocumentReferenceProvider
import com.example.gymmanager.view.dialogs.FeeDialog
import com.example.gymmanager.view.dialogs.SimpleConfirmationDialog
import com.example.gymmanager.view.validation_notifier_edittext.ValidationNotifierEditText
import com.example.gymmanager.viewmodel.MemberDetailsFragmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
    }

    fun submitFees(view: View) {
        val fab = view as FloatingActionButton
        fab.isClickable = false
        FeeDialog().apply {
            doOnDialogClosed { fab.isClickable = true }
            doOnFeeSubmit { amount, months ->
                binding.feeFabProgressCircle.show()
                viewModel.viewModelScope.launch {
                    val isFeeSubmitted = viewModel.submitFees(amount, months)
                    if (isFeeSubmitted) {
                        binding.feeFabProgressCircle.beginFinalAnimation()
                    } else {
                        Toast.makeText(
                            this@MemberDetailsFragment.requireActivity(),
                            "fee submission failed,try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    binding.feeFabProgressCircle.hide()
                    fab.isClickable = true
                }
            }
        }
        .show(requireActivity().supportFragmentManager, null)
    }

    fun deleteMember(view: View){

        SimpleConfirmationDialog.
        withText("Are you sure you want to delete this member").apply {
            val fab = view as FloatingActionButton
            doOnAccepted {
                fab.isClickable = false
                binding.deleteFabProgressCircle.show()
                lifecycleScope.launch {
                    val isMemberDeleted = viewModel.deleteMember()
                    if(isMemberDeleted)
                    {
                        Log.d("log","navigating")
                      val action = MemberDetailsFragmentDirections.actionMemberDetailsFragmentToMainFragment()
                      findNavController().navigate(action)
                    }else{
                        Toast.makeText(
                            this@MemberDetailsFragment.requireActivity(),
                            "member deletion failed,try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }.show(requireActivity().supportFragmentManager,null)
    }

    fun callMember(){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${viewModel.memberDetails.value?.phone}"))
        startActivity(intent)
    }

    fun editPressed(
        validationNotifierEditText: ValidationNotifierEditText,
        view: View,
        documentReferenceProvider: DocumentReferenceProvider
    ) {
        ValidationNotifierEditTextAndEditButtonMediator
            .setValidationNotifierEditText(validationNotifierEditText)
            .setEditButton(view as MemberDetailsEditButton)
            .newDataHolder(documentReferenceProvider)
            .setup()
    }
}