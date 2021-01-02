package com.example.gymmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.gymmanager.databinding.MemberRowBinding
import com.example.gymmanager.view.MainFragmentDirections


import com.example.gymmanager.model.ConciseMember
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import java.util.*

class MembersRecyclerViewAdapter(options: FirestorePagingOptions<ConciseMember>) :
    FirestorePagingAdapter<ConciseMember, MembersRecyclerViewAdapter.ViewHolder>(options) {
    interface ItemClickListener {
       fun onItemClicked(member : ConciseMember)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowBinding = MemberRowBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(rowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, conciseMember: ConciseMember) {
        holder.bind(conciseMember)

    }

    class ViewHolder(private val rowBinding : MemberRowBinding) : RecyclerView.ViewHolder(rowBinding.root) {
        fun bind(member: ConciseMember) {
            rowBinding.member = member
            rowBinding.executePendingBindings()
            val action = MainFragmentDirections.actionMainFragmentToMemberDetailsFragment(member)
            val listener = Navigation.createNavigateOnClickListener(action)
            itemView.setOnClickListener(listener)
        }
    }

}