package com.example.gymmanager.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.gymmanager.R
import com.example.gymmanager.adapter.MainFragmentViewPagerAdapter
import com.example.gymmanager.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment(R.layout.fragment_main){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)
        binding.addMemberFAB.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAddMemberFragment()
            findNavController().navigate(action)
        }

        binding.viewpager2.adapter = MainFragmentViewPagerAdapter(this)
        TabLayoutMediator(binding.viewpager2Tabs,binding.viewpager2){ tab,position ->
          tab.text = position.toString()
        }.attach()
    }
}