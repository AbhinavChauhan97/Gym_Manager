package com.example.gymmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.gymmanager.view.memberviewholderview.Member
import java.util.*

class MembersFragmentViewModel : ViewModel(){

    val membersList = LinkedList<Member>()
    init {
        membersList.add(Member("goldy","img", "10"))
        membersList.add(Member("goldy gujjar","img", "11"))
        membersList.add(Member("goldy ji","img","12"))
    }
}