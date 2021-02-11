package com.example.gymmanager.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.example.gymmanager.R
import com.example.gymmanager.databinding.MemberRowBinding
import com.example.gymmanager.generated.callback.OnClickListener
import com.example.gymmanager.view.MainFragmentDirections


import com.example.gymmanager.model.ConciseMember
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.DocumentSnapshot
import org.threeten.bp.LocalDate

class MembersRecyclerViewAdapter(options: FirestorePagingOptions<ConciseMember>) :
    FirestorePagingAdapter<ConciseMember, MembersRecyclerViewAdapter.ViewHolder>(options) {

    var onCurrentListChange:((PagedList<DocumentSnapshot>) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowBinding = MemberRowBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(rowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, conciseMember: ConciseMember) {
        //Log.d("log", "onBindViewHolder:$conciseMember ")
        holder.bind(conciseMember)
    }

    override fun onCurrentListChanged(currentList: PagedList<DocumentSnapshot>?) {
        if (currentList != null) {
            onCurrentListChange?.invoke(currentList)
        }
        super.onCurrentListChanged(currentList)
    }

    class ViewHolder(private val rowBinding : MemberRowBinding) : RecyclerView.ViewHolder(rowBinding.root) {

        fun bind(member: ConciseMember) {
            rowBinding.member = member
            val feeDate:LocalDate = LocalDate.of(member.year,member.month,member.day)
            val daysLeft = feeDate - LocalDate.now()
            rowBinding.daysLeft = daysLeft
            rowBinding.executePendingBindings()
            val action = MainFragmentDirections.actionMainFragmentToMemberDetailsFragment(member)
            
            val listener = Navigation.createNavigateOnClickListener(action)
            itemView.setOnClickListener(listener)
        }
    }
    fun doWhenCurrentListChanges(onCurrentListChange:(PagedList<DocumentSnapshot>) -> Unit){
        this.onCurrentListChange = onCurrentListChange
    }
}

private operator fun LocalDate.minus(other: LocalDate) = toEpochDay() - other.toEpochDay()

