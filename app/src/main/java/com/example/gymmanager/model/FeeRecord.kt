package com.example.gymmanager.model

import org.threeten.bp.LocalDate

data class FeeRecord(val id:String,val name:String,val image:String?,val amount:String) {
    val day:Int
    val month:Int
    val year:Int
    constructor():this("","","","")
    init {
        val localDate = LocalDate.now()
        day = localDate.dayOfMonth
        month = localDate.monthValue
        year = localDate.year
    }
}