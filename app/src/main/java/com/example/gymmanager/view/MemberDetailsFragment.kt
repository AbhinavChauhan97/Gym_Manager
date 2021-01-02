package com.example.gymmanager.view

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.example.gymmanager.R
import com.example.gymmanager.databinding.FragmentMemberDetailsBinding
import com.example.gymmanager.model.MemberDetails
import com.example.gymmanager.repository.DocumentReferenceProvider
import com.example.gymmanager.repository.downloadMember
import com.example.gymmanager.view.validation_notifier_edittext.ValidationNotifierEditText
import com.example.gymmanager.viewmodel.MemberDetailsFragmentViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
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
        viewModel.viewModelScope.launch {
            val memberDocument = downloadMember(args.conciseMember.id)
            if(memberDocument.exists()) {
                viewModel.memberDetails.value = memberDocument.toObject(MemberDetails::class.java)
            }
        }
        val imagePresenterFragment = requireActivity().supportFragmentManager.findFragmentByTag("image_presenter") as ImagePresenterFragment

    }

    fun editPressed(validationNotifierEditText: ValidationNotifierEditText,view: View,documentReferenceProvider: DocumentReferenceProvider) {
      ValidationNotifierEditTextAndEditButtonMediator
          .setValidationNotifierEditText(validationNotifierEditText)
          .setEditButton(view as MemberDetailsEditButton)
          .newDataHolder(documentReferenceProvider)
          .setup()
    }
}