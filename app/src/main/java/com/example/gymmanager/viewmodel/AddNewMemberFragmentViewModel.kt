package com.example.gymmanager.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.gymmanager.model.ConciseMember

import com.example.gymmanager.model.MemberDetails
import com.example.gymmanager.repository.*

import com.google.firebase.firestore.FirebaseFirestore


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
            isImageUploaded = saveImage(newMember.image!!)  // improvement
        }
        val isMemberDataAdded: Boolean = addToFireStore(newMember, newMemberDetails)
        if ((imageClicked && isImageUploaded) || isMemberDataAdded) {
            isMemberSaved = true
            return true
        }
        return false
    }
}

