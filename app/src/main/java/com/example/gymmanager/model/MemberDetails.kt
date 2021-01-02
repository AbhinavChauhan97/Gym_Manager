package com.example.gymmanager.model

import androidx.lifecycle.MutableLiveData
import com.example.gymmanager.repository.DocumentReferenceProvider
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FirebaseFirestore

data class MemberDetails(val id:String):DocumentReferenceProvider {
    constructor():this("")
    var phone = ""
    var address = ""
    override val documentReference: DocumentReference
        @Exclude get() = FirebaseFirestore.getInstance().collection("d_members")
            .document(id)
}