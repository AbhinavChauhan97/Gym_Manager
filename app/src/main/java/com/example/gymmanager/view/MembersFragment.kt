package com.example.gymmanager.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymmanager.R
import com.example.gymmanager.adapter.MembersRecyclerViewAdapter
import com.example.gymmanager.databinding.FragmentMembersBinding
import com.example.gymmanager.model.ConciseMember
import com.example.gymmanager.viewmodel.MembersFragmentViewModel
import com.firebase.ui.firestore.paging.FirestorePagingOptions


class MembersFragment : Fragment(R.layout.fragment_members){

    private lateinit var membersFragmentViewModel: MembersFragmentViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        membersFragmentViewModel = ViewModelProviders.of(this).get(MembersFragmentViewModel::class.java)
        val binding = FragmentMembersBinding.bind(view)
        val options = FirestorePagingOptions.Builder<ConciseMember>()
            .setQuery(membersFragmentViewModel.baseQuery,membersFragmentViewModel.pagedListConfig,
                ConciseMember::class.java)
            .setLifecycleOwner(this)
            .build()
        binding.membersRecyclerview.layoutManager = LinearLayoutManager(requireActivity())
        binding.membersRecyclerview.adapter = MembersRecyclerViewAdapter(options)
        binding.membersRecyclerview.addItemDecoration(DividerItemDecoration(requireActivity(),LinearLayoutManager.VERTICAL))

    }

}