package com.example.gymmanager.model

import androidx.lifecycle.MutableLiveData
import com.example.gymmanager.repository.DocumentReferenceProvider
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FirebaseFirestore
import org.threeten.bp.LocalDate
import java.io.Serializable
import java.lang.reflect.Member


data class ConciseMember(val id: String): Serializable,DocumentReferenceProvider {

    constructor() : this("")
    @Transient
    @get:Exclude
    val date: LocalDate = LocalDate.now()
    val day: Int = date.dayOfMonth
    val month: Int = date.monthValue
    val year: Int = date.year
    var image: String? = null
    var hasImage = false
    var name = "" //= MutableLiveData<String>()
    override val documentReference: DocumentReference
        @Exclude
        get() = FirebaseFirestore.getInstance().collection("c_members")
            .document(id)


}