package com.example.gymmanager.model

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Exclude
import java.util.*

data class DetailedMember(@Exclude val  id:String) {

    constructor() : this("")
    @get:Exclude
    var conciseMember = ConciseMember(id)
    var phone = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var day = Calendar.DAY_OF_MONTH
    var month = Calendar.MONTH
    var year = Calendar.YEAR
    var hasImage = false
    var name = ""
    var image = ""
}