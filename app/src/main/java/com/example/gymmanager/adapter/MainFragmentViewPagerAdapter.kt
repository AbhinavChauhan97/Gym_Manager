package com.example.gymmanager.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.arlib.floatingsearchview.FloatingSearchView
import com.example.gymmanager.view.MembersFeeRecordsFragment
import com.example.gymmanager.view.MembersFragment
import java.util.*

class MainFragmentViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    val fragmentsList = LinkedList<Fragment>()

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val membersFragment = MembersFragment()
                fragmentsList.add(membersFragment)
                membersFragment
            }
            else ->{
                val feeRecordFragment = MembersFeeRecordsFragment()
                fragmentsList.add(feeRecordFragment)
                feeRecordFragment
            }
        }
    }
}