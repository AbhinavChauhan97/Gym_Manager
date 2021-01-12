package com.example.gymmanager.viewmodel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymmanager.model.ConciseMember
import com.example.gymmanager.model.DetailedMember
import com.example.gymmanager.model.FeeRecord
import com.example.gymmanager.model.MemberDetails
import com.example.gymmanager.repository.deleteMember
import com.example.gymmanager.repository.downloadMember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import com.example.gymmanager.repository.submitFee as submitMemberFees
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.function.BinaryOperator

class MemberDetailsFragmentViewModel : ViewModel() {

   // var detailedMember: MutableLiveData<DetailedMember> = MutableLiveData()
     lateinit var conciseMember: ConciseMember
     var memberDetails = MutableLiveData<MemberDetails?>() // MemberDetails(conciseMember.id)

     fun loadMember(memberId:String){
         viewModelScope.launch {
             val memberDocument = downloadMember(memberId)
             if(memberDocument.exists()) {
                 memberDetails.value = memberDocument.toObject(MemberDetails::class.java)
             }
         }
     }

    suspend fun deleteMember():Boolean{
        return viewModelScope.async(Dispatchers.IO){
            Log.d("log","deleting viemodel")
            deleteMember(conciseMember.id)
        }.await()
    }

    suspend fun submitFees(amount:String,months:String):Boolean{
        val feeRecord = FeeRecord(id = conciseMember.id,
            name = conciseMember.name,
            image = conciseMember.image,
            amount = amount)
        val deferredResult = viewModelScope.async {  submitMemberFees(feeRecord,months) }
        return deferredResult.await()
    }
}