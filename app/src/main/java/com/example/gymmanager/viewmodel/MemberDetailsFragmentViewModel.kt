package com.example.gymmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gymmanager.model.ConciseMember
import com.example.gymmanager.model.DetailedMember
import com.example.gymmanager.model.MemberDetails
import com.example.gymmanager.repository.downloadMember

class MemberDetailsFragmentViewModel : ViewModel() {

   // var detailedMember: MutableLiveData<DetailedMember> = MutableLiveData()
     lateinit var conciseMember: ConciseMember
     var memberDetails = MutableLiveData<MemberDetails?>() // MemberDetails(conciseMember.id)

}