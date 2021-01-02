package com.example.gymmanager.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.gymmanager.R
import com.example.gymmanager.databinding.FragmentAddNewMemberBinding
import com.example.gymmanager.util.*
import com.example.gymmanager.view.enabled_disabled_behaviour_accepter_floating_action_button.AddingFABEnabledDisabledBehaviour
import com.example.gymmanager.view.validation_notifier_edittext.ValidationNotifierEditText
import com.example.gymmanager.view.validation_notifier_edittext.ValidationNotifierEditTextGroup
import com.example.gymmanager.viewmodel.AddNewMemberFragmentViewModel
import com.github.jorgecastilloprz.listeners.FABProgressListener
import kotlinx.coroutines.*


class AddMemberFragment : Fragment(R.layout.fragment_add_new_member) {

    private lateinit var viewModel: AddNewMemberFragmentViewModel
    lateinit var binding: FragmentAddNewMemberBinding

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            viewModel.imageClicked = true
            binding.image.setImageURI(null)
            binding.image.load(viewModel.imageUri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddNewMemberFragmentViewModel::class.java)
        binding = FragmentAddNewMemberBinding.bind(view)
        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        binding.name.addValidationChangeListener(binding.nameCardview)
        binding.phone.addValidationChangeListener(binding.phoneCardview)
        binding.address.addValidationChangeListener(binding.addressCardview)
        binding.addMemberFAB.fabEnabledDisabledBehaviour =
            AddingFABEnabledDisabledBehaviour(binding.addMemberFAB)
        binding.addMemberFAB.setOnClickListener { addMember() }
        binding.addMemberFAB.isEnabled = false

        ValidationNotifierEditTextGroup
            .createGroupOf(binding.name, binding.phone, binding.address)
            .setValidationListenerOnGroup(
                object : ValidationNotifierEditTextGroup.ValidationEditTextGroupValidationListener {
                    override fun onAllBecomeValid() {
                        binding.addMemberFAB.isEnabled = true
                    }

                    override fun onAnyBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText) {
                        binding.addMemberFAB.isEnabled = false
                    }
                })

    }

    fun launchCamera() {
        if (viewModel.imageUri == null) {
            viewModel.imageUri = createUriForImageFile("${viewModel.newMember.id}.jpg")
        }
        cameraLauncher.launch(viewModel.imageUri)
    }

    private fun addMember() {
        binding.fabProgressCircle.show()
        binding.fabProgressCircle.attachListener {
            val action = AddMemberFragmentDirections.actionAddMemberFragmentToMainFragment()
            findNavController().navigate(action)
        }
        viewModel.viewModelScope.launch {
            viewModel.isMemberSaved = viewModel.addMember()
            if (viewModel.isMemberSaved) {
                binding.fabProgressCircle.beginFinalAnimation()
            } else {
                Toast.makeText(
                    this@AddMemberFragment.requireActivity(),
                    getString(R.string.failed_adding_member),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        GlobalScope.launch {
            if (viewModel.imageClicked && !viewModel.isMemberSaved) {
                deleteImage("${viewModel.newMember.id}.jpg")
            }
        }
    }
}