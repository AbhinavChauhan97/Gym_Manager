package com.example.gymmanager.model

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Exclude
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.LocalDate
import java.util.*

data class DetailedMember(@Exclude val  id:String) {

    var name = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var day = Calendar.DAY_OF_MONTH
    var month = Calendar.MONTH
    var year = Calendar.YEAR

}