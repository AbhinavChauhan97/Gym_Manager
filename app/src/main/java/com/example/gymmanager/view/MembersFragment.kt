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
import com.example.gymmanager.adapter.MembersRecyclerViewAdapter
import com.example.gymmanager.databinding.FragmentMembersBinding
import com.example.gymmanager.model.ConciseMember
import com.example.gymmanager.repository.getALlMembersFeeReference
import com.example.gymmanager.repository.getConciseMembersReference
import com.example.gymmanager.util.PagingOptionsSupplier
import com.example.gymmanager.util.QuerySupplier
import com.example.gymmanager.viewmodel.MembersFragmentViewModel
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MembersFragment : Fragment(R.layout.fragment_members) {

    private lateinit var options: FirestorePagingOptions<ConciseMember>
    lateinit var binding: FragmentMembersBinding
    private lateinit var membersFragmentViewModel: MembersFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Log.d("log","members fragment created")


        membersFragmentViewModel =
            ViewModelProviders.of(requireActivity()).get(MembersFragmentViewModel::class.java)
        binding = FragmentMembersBinding.bind(view)
        binding.membersRecyclerview.layoutManager = LinearLayoutManager(requireActivity())
        binding.membersRecyclerview.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

        lifecycleScope.launch(Dispatchers.Main) {
            val sorting = MyApplication.dataStore.data.first()[MyApplication.membersSortingKey]
            options = if (sorting == MyApplication.SORT_BY_NAME) {
                getPagingOptionsForQuery(getConciseMembersReference().orderBy("name"))
            } else {
                getPagingOptionsForQuery(
                    getConciseMembersReference()
                        .orderBy("year")
                        .orderBy("month")
                        .orderBy("day"))
            }
            binding.membersRecyclerview.adapter = MembersRecyclerViewAdapter(options)
            //Log.d("log",sorting.toString())
        }

        QuerySupplier.listenForQueries(object : QuerySupplier.QuerySupporter {
            override fun newQuery(query: Query) {
                membersFragmentViewModel.baseQuery = query
                options = getPagingOptionsForQuery(membersFragmentViewModel.baseQuery)
                binding.membersRecyclerview.adapter = MembersRecyclerViewAdapter(options)
            }

            override fun queryOfInterest() = QuerySupplier.QueryType.CONCISE_MEMBERS
        })
    }

    private fun getPagingOptionsForQuery(query: Query): FirestorePagingOptions<ConciseMember> {
        return PagingOptionsSupplier(this, ConciseMember::class.java).getPagingOptions(query)
    }
}