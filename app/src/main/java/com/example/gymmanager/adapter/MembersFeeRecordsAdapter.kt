package com.example.gymmanager.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gymmanager.databinding.FeeRowBinding
import com.example.gymmanager.model.ConciseMember
import com.example.gymmanager.model.FeeRecord
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions

class MembersFeeRecordsAdapter(options: FirestorePagingOptions<FeeRecord>) :
    FirestorePagingAdapter<FeeRecord, MembersFeeRecordsAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FeeRowBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, positin: Int, feeRecord: FeeRecord) {
        holder.bind(feeRecord)
    }

    class ViewHolder(private val rowBinding: FeeRowBinding) :
        RecyclerView.ViewHolder(rowBinding.root) {
        fun bind(feeRecord: FeeRecord) {
            feeRecord.apply {  }
            rowBinding.feeRecord = feeRecord
            rowBinding.executePendingBindings()
        }
    }
}