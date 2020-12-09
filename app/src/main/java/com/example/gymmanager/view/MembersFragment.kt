package com.example.gymmanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymmanager.adapter.MembersRecyclerViewAdapter
import com.example.gymmanager.databinding.MembersRecyclerviewBinding
import com.example.gymmanager.viewmodel.MembersFragmentViewModel

class MembersFragment : Fragment(){

    private lateinit var memberFragmentViewModel:MembersFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memberFragmentViewModel = ViewModelProviders.of(this).get(MembersFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = MembersRecyclerviewBinding.inflate(inflater,container,false)
        binding.membersRecyclerview.layoutManager = LinearLayoutManager(activity)
        binding.membersRecyclerview.adapter = MembersRecyclerViewAdapter(memberFragmentViewModel.membersList)
        return binding.root
    }

}