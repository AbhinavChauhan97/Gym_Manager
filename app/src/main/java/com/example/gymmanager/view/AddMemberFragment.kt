package com.example.gymmanager.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.gymmanager.R
import com.example.gymmanager.databinding.AddNewMemberBinding
import com.example.gymmanager.util.compressAndScaleImage
import com.example.gymmanager.util.compressImage
import com.example.gymmanager.util.createUriForImageFile
import com.example.gymmanager.util.resizeImage
import com.example.gymmanager.view.supercooledittext.SupercoolEditText
import com.example.gymmanager.viewmodel.AddNewMemberFragmentViewModel


class AddMemberFragment : Fragment(R.layout.add_new_member) {

    private lateinit var viewModel: AddNewMemberFragmentViewModel
    lateinit var binding: AddNewMemberBinding


    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {

        if (it == true) {
            //compressImage(viewModel.member.id,requireActivity())
            viewModel.imageTaken = true
            binding.image.setImageURI(null)
            val bitmap = compressAndScaleImage(requireActivity(),viewModel.member.id,binding.image.height,binding.image.width)
            binding.image.setImageBitmap(bitmap)

           /* Glide.with(this)
                .load(viewModel.imageUri)
                .override(binding.image.width, binding.image.height)
                .centerCrop()
                .into(binding.image)*/
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddNewMemberFragmentViewModel::class.java)
        binding = AddNewMemberBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.name.validationStateChangeListener =
            object : SupercoolEditText.ValidationStateChangeListener {
                override fun onValidationStateChanged(valid: Boolean) {
                    viewModel.memberNameIsValid.value = valid
                    if (valid) binding.nameCardview.setBorderColor(Color.GREEN) else binding.nameCardview.setBorderColor(Color.RED)
                    fabReact()
                }
            }


        binding.phone.validationStateChangeListener =
            object : SupercoolEditText.ValidationStateChangeListener {
                override fun onValidationStateChanged(valid: Boolean) {
                    viewModel.memberPhoneIsValid.value = valid
                    if (valid) binding.phoneCardview.setBorderColor(Color.GREEN) else binding.phoneCardview.setBorderColor(Color.RED)
                    fabReact()
                }
            }

        binding.address.validationStateChangeListener =
            object : SupercoolEditText.ValidationStateChangeListener {
                override fun onValidationStateChanged(valid: Boolean) {
                    viewModel.memberAddressIsValid.value = valid
                    if (valid) binding.addressCardview.setBorderColor(Color.GREEN) else binding.addressCardview.setBorderColor(Color.RED)
                    fabReact()
                }
            }

        binding.clickImageButton.setOnClickListener {
            if(viewModel.imageUri == null) {
                viewModel.imageUri = createUriForImageFile(requireActivity(), viewModel.member.id)
            }
            cameraLauncher.launch(viewModel.imageUri)
        }
    }


    private fun fabReact() {
        if (viewModel.isMemberDetailsValid()) {
            binding.addMemberFAB.apply {
                backgroundTintList = ColorStateList.valueOf(Color.GREEN)
                isClickable = true
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_baseline_check_24
                    )
                )
            }
        } else {
            binding.addMemberFAB.apply {
                backgroundTintList = ColorStateList.valueOf(Color.RED)
                isClickable = false
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_baseline_clear_24
                    )
                )
            }
        }
    }

}