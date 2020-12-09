package com.example.gymmanager.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gymmanager.model.DetailedMember
import com.example.gymmanager.repository.addMemberToFirestoreDatabase
import com.google.firebase.firestore.FirebaseFirestore



class AddNewMemberFragmentViewModel : ViewModel() {

    private val memberId = FirebaseFirestore.getInstance()
        .collection("users")
        .document()
        .id + ".jpg"
    val member = DetailedMember(memberId)
    var memberNameIsValid = MutableLiveData(false)
    var memberPhoneIsValid = MutableLiveData(false)
    var memberAddressIsValid = MutableLiveData(false)
    var imageTaken = false
    var imageUri: Uri? = null

    fun addMember() {
        if(imageTaken) {
            addMemberToFirestoreDatabase(member, imageUri)
        }else{
            addMemberToFirestoreDatabase(member)
        }
    }

    fun isMemberDetailsValid(): Boolean {
        return memberNameIsValid.value!! && memberPhoneIsValid.value!! && memberAddressIsValid.value!!
    }

}

