package com.example.gymmanager.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

import com.example.gymmanager.R
import com.example.gymmanager.adapter.MainFragmentViewPagerAdapter
import com.example.gymmanager.databinding.FragmentMainBinding
import com.example.gymmanager.model.ConciseMember
import com.example.gymmanager.model.FeeRecord
import com.example.gymmanager.repository.getALlMembersFeeReference
import com.example.gymmanager.repository.getConciseMembersReference
import com.example.gymmanager.repository.getMembers
import com.example.gymmanager.util.PagingOptionsSupplier
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class MainFragment : Fragment(R.layout.fragment_main) {

    var currentPosition = 0

    lateinit var viewPagerAdapter: MainFragmentViewPagerAdapter
    private val queryTextListener =
        FloatingSearchView.OnQueryChangeListener { _, newQuery ->
            viewPagerAdapter.showInCurrentFragment(newQuery,currentPosition)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)
        binding.floatingSearchView.setOnQueryChangeListener(queryTextListener)
        binding.floatingSearchView.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.sort -> {
                    Log.d("log", "onViewCreated: sort by name")
                }
                R.id.sort_by_fees -> {

                }
            }
        }

        binding.addMemberFAB.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAddMemberFragment()
            findNavController().navigate(action)
        }

        binding.viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPosition = position
            }
        })

        viewPagerAdapter = MainFragmentViewPagerAdapter(this)
        binding.viewpager2.adapter = viewPagerAdapter
        TabLayoutMediator(binding.viewpager2Tabs,binding.viewpager2){ tab,position ->
          tab.text = getTitleForPosition(position)
        }.attach()
    }

    private fun getTitleForPosition(position : Int):String{
        return when(position){
            0 -> "ALL MEMBERS"
            else -> "FEE RECORDS"
        }
    }
}