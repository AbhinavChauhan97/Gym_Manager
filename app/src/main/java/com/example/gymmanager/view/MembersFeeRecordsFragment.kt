package com.example.gymmanager.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymmanager.R
import com.example.gymmanager.adapter.MembersFeeRecordsAdapter
import com.example.gymmanager.databinding.FragmentMembersBinding
import com.example.gymmanager.databinding.LayoutFeeRecordsFragmentBinding
import com.example.gymmanager.model.ConciseMember
import com.example.gymmanager.model.FeeRecord
import com.example.gymmanager.repository.getALlMembersFeeReference
import com.example.gymmanager.repository.getConciseMemberDoc
import com.example.gymmanager.repository.getConciseMembersReference
import com.example.gymmanager.util.PagingOptionsSupplier
import com.example.gymmanager.viewmodel.MembersFeeRecordsFragmentViewModel
import com.firebase.ui.firestore.paging.FirestorePagingOptions

class MembersFeeRecordsFragment:Fragment(R.layout.layout_fee_records_fragment) {

    lateinit var membersFeeRecordsFragmentViewModel:MembersFeeRecordsFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("log","fee records fragment created")
        val binding = LayoutFeeRecordsFragmentBinding.bind(view)

        membersFeeRecordsFragmentViewModel = ViewModelProviders.of(this).get(MembersFeeRecordsFragmentViewModel::class.java)

        val options = PagingOptionsSupplier(this,FeeRecord::class.java).apply {
            query = getALlMembersFeeReference().orderBy("day")
        }.getPagingOptions()

        binding.feeRecordsRecyclerview.layoutManager = LinearLayoutManager(requireActivity())
        binding.feeRecordsRecyclerview.adapter = MembersFeeRecordsAdapter(options)
        binding.feeRecordsRecyclerview.addItemDecoration(DividerItemDecoration(requireActivity(),LinearLayoutManager.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        Log.d("log","feerecordfragment visible")
    }

}