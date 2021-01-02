package com.example.gymmanager.model

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Member(val id: String) {

    var name = MutableLiveData<String>()
}