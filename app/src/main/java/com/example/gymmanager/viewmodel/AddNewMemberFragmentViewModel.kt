package com.example.gymmanager.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymmanager.model.ConciseMember

import com.example.gymmanager.model.DetailedMember
import com.example.gymmanager.model.Member
import com.example.gymmanager.model.MemberDetails
import com.example.gymmanager.repository.*

import com.example.gymmanager.util.compressAndResizeImage
import com.example.gymmanager.util.createUriForImageFile
import com.example.gymmanager.util.uploadImage
import com.example.gymmanager.view.AddMemberFragmentDirections
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


class AddNewMemberFragmentViewModel : ViewModel() {

    private val newMemberId = FirebaseFirestore.getInstance()
        .collection("c_members")
        .document()
        .id
    var imageClicked = false
    val newMember = ConciseMember(newMemberId)
    val newMemberDetails = MemberDetails(newMemberId)
    var imageUri: Uri? = null
    var isMemberSaved = false

    suspend fun addMember(): Boolean {
        var isImageUploaded = false
        if (imageClicked) {
            newMember.image = "$newMemberId.jpg"
            isImageUploaded = saveImage(newMember.image!!)
        }
        val isMemberDataAdded: Boolean = addToFireStore(newMember, newMemberDetails)
        if ((imageClicked && isImageUploaded) || isMemberDataAdded) {
            isMemberSaved = true
            return true
        }
        return false
    }
}

