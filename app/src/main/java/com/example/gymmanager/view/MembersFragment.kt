package com.example.gymmanager.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymmanager.R
import com.example.gymmanager.adapter.MainFragmentViewPagerAdapter
import com.example.gymmanager.adapter.MembersRecyclerViewAdapter
import com.example.gymmanager.databinding.FragmentMembersBinding
import com.example.gymmanager.model.ConciseMember
import com.example.gymmanager.repository.getConciseMembersReference
import com.example.gymmanager.util.PagingOptionsSupplier
import com.example.gymmanager.viewmodel.MembersFragmentViewModel
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.Query


class MembersFragment : Fragment(R.layout.fragment_members),MainFragmentViewPagerAdapter.SearchSupporter {

    private lateinit var options: FirestorePagingOptions<ConciseMember>
    lateinit var binding: FragmentMembersBinding
    private lateinit var membersFragmentViewModel: MembersFragmentViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Log.d("log","members fragment created")

        membersFragmentViewModel =
            ViewModelProviders.of(this).get(MembersFragmentViewModel::class.java)
        binding = FragmentMembersBinding.bind(view)
        options = getPagingOptionsForQuery(membersFragmentViewModel.baseQuery)
        binding.membersRecyclerview.layoutManager = LinearLayoutManager(requireActivity())
        binding.membersRecyclerview.adapter = MembersRecyclerViewAdapter(options)
        binding.membersRecyclerview.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )

    }

    override fun search(query: String) {
        val baseQuery = if(query.isBlank()) membersFragmentViewModel.baseQuery else getConciseMembersReference().whereEqualTo("name",query)
        options = getPagingOptionsForQuery(baseQuery)
        binding.membersRecyclerview.adapter = MembersRecyclerViewAdapter(options)
    }
    private fun getPagingOptionsForQuery(query: Query):FirestorePagingOptions<ConciseMember>{
        return PagingOptionsSupplier(this,ConciseMember::class.java).apply {
            this.query = query
        }.getPagingOptions()
    }
}