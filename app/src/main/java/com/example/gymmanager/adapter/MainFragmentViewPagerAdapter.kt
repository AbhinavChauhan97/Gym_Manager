package com.example.gymmanager.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.arlib.floatingsearchview.FloatingSearchView
import com.example.gymmanager.model.ConciseMember
import com.example.gymmanager.view.MembersFeeRecordsFragment
import com.example.gymmanager.view.MembersFragment
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.core.Query
import java.util.*

class MainFragmentViewPagerAdapter(val fragment: Fragment) : FragmentStateAdapter(fragment) {


    interface SearchSupporter{
        fun search(query:String)
    }

    fun showInCurrentFragment(memberName:String,currentFragmentIndex:Int){
       val  currentFragment = fragment.childFragmentManager.findFragmentByTag("f$currentFragmentIndex")
        if(currentFragment is SearchSupporter){
            currentFragment.search(memberName)
        }
    }

    override fun getItemCount(): Int {
        return 2
    }


    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                val membersFragment = MembersFragment()
                membersFragment
            }
            else ->{
                val feeRecordFragment = MembersFeeRecordsFragment()
                feeRecordFragment
            }
        }
    }
}