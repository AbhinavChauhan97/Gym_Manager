package com.example.gymmanager.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gymmanager.R


import com.example.gymmanager.view.memberviewholderview.Member
import com.example.gymmanager.view.memberviewholderview.MemberViewHolderView
import java.util.*

class MembersRecyclerViewAdapter(private val membersList:LinkedList<Member>) : RecyclerView.Adapter<MembersRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.member_itemview_layout,parent,false) as MemberViewHolderView
        return ViewHolder(view)
   }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(membersList[position])

    }

    override fun getItemCount(): Int {
        return membersList.size
    }

    inner class ViewHolder(private val view:MemberViewHolderView) : RecyclerView.ViewHolder(view){
      fun bind(member: Member){
          view.member = member
      }
    }
}