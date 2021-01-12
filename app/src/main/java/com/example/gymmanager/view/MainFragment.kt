package com.example.gymmanager.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

import com.example.gymmanager.R
import com.example.gymmanager.adapter.MainFragmentViewPagerAdapter
import com.example.gymmanager.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class MainFragment : Fragment(R.layout.fragment_main){

    interface VisibilityNotifier{
        fun onVisible()
    }
    val searchListener = object : FloatingSearchView.OnSearchListener{
        override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
        }
        override fun onSearchAction(currentQuery: String) {
        }
    }
    val searchActionList = LinkedList<(String) -> Int>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)
        binding.addMemberFAB.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAddMemberFragment()
            findNavController().navigate(action)
        }
        val viewPagerAdapter = MainFragmentViewPagerAdapter(this)
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
    fun addOnSearchAction(doOnSearch:(searchQuery:String) -> Int){
        searchActionList.add(doOnSearch)
    }
}