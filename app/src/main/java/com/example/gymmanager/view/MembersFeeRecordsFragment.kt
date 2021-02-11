package com.example.gymmanager.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymmanager.MyApplication
import com.example.gymmanager.R
import com.example.gymmanager.adapter.MainFragmentViewPagerAdapter
import com.example.gymmanager.adapter.MembersFeeRecordsAdapter
import com.example.gymmanager.databinding.FragmentMembersBinding
import com.example.gymmanager.databinding.LayoutFeeRecordsFragmentBinding
import com.example.gymmanager.model.ConciseMember
import com.example.gymmanager.model.FeeRecord
import com.example.gymmanager.repository.getALlMembersFeeReference
import com.example.gymmanager.repository.getConciseMember
import com.example.gymmanager.repository.getConciseMemberDoc
import com.example.gymmanager.repository.getConciseMembersReference
import com.example.gymmanager.util.PagingOptionsSupplier
import com.example.gymmanager.util.QuerySupplier
import com.example.gymmanager.viewmodel.MembersFeeRecordsFragmentViewModel
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MembersFeeRecordsFragment:Fragment(R.layout.layout_fee_records_fragment)  {

    lateinit var membersFeeRecordsFragmentViewModel:MembersFeeRecordsFragmentViewModel
    lateinit var options: FirestorePagingOptions<FeeRecord>
    lateinit var binding:LayoutFeeRecordsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Log.d("log","fee records fragment created")
        binding = LayoutFeeRecordsFragmentBinding.bind(view)

        membersFeeRecordsFragmentViewModel = ViewModelProviders.of(this).get(MembersFeeRecordsFragmentViewModel::class.java)

        binding.feeRecordsRecyclerview.layoutManager = LinearLayoutManager(requireActivity())
        binding.feeRecordsRecyclerview.addItemDecoration(DividerItemDecoration(requireActivity(),LinearLayoutManager.VERTICAL))

        lifecycleScope.launch(Dispatchers.Main) {
            val sorting = MyApplication.dataStore.data.first()[MyApplication.feesSortingKey]
            options = if(sorting == MyApplication.SORT_BY_NAME) {
                getPagingOptionsForQuery(getALlMembersFeeReference().orderBy("name"))
            }
            else {
                getPagingOptionsForQuery(getALlMembersFeeReference()
                    .orderBy("day")
                    .orderBy("month")
                    .orderBy("year"))
            }
        }
        options = getPagingOptionsForQuery(membersFeeRecordsFragmentViewModel.baseQuery)
        binding.feeRecordsRecyclerview.adapter = MembersFeeRecordsAdapter(options)

        QuerySupplier.listenForQueries(object : QuerySupplier.QuerySupporter{
            override fun newQuery(query: Query) {
                membersFeeRecordsFragmentViewModel.baseQuery = query
                options = getPagingOptionsForQuery(query)
                binding.feeRecordsRecyclerview.adapter = MembersFeeRecordsAdapter(options)
            }
            override fun queryOfInterest() = QuerySupplier.QueryType.FEES
        })
    }

    private fun getPagingOptionsForQuery(query: Query):FirestorePagingOptions<FeeRecord>{
        return PagingOptionsSupplier(this,FeeRecord::class.java).getPagingOptions(query)
    }

}