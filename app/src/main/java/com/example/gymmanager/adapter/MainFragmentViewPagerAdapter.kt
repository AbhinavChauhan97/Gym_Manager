package com.example.gymmanager.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gymmanager.view.MembersFragment

class MainFragmentViewPagerAdapter(fragment:Fragment) : FragmentStateAdapter(fragment){

    override fun getItemCount(): Int {
        return 1
    }

    override fun createFragment(position: Int): Fragment {
        //Log.d("log","creating fragment")
        return MembersFragment()
    }

}