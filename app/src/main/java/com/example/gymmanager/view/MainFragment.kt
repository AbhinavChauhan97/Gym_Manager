package com.example.gymmanager.view

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.allViews
import androidx.core.view.get
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.arlib.floatingsearchview.FloatingSearchView
import com.example.gymmanager.MyApplication
import com.example.gymmanager.R
import com.example.gymmanager.adapter.MainFragmentViewPagerAdapter
import com.example.gymmanager.databinding.FragmentMainBinding
import com.example.gymmanager.util.QuerySupplier
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainFragment : Fragment(R.layout.fragment_main) {


    private var currentPosition = 0

    private val queryTextListener =
        FloatingSearchView.OnQueryChangeListener { _, newQuery ->
            QuerySupplier.newSearchQuery(newQuery, getQueryType())
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)
        binding.floatingSearchView.setOnQueryChangeListener(queryTextListener)
        binding.floatingSearchView.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sort -> {
                    var anchorView: View? = null
                    binding.floatingSearchView.allViews.forEach {
                        if (it.javaClass.name == "com.arlib.floatingsearchview.util.view.MenuView") {
                            anchorView = it
                        }
                    }
                    val popupMenu = PopupMenu(requireActivity(), anchorView!!)

                    if (getQueryType() == QuerySupplier.QueryType.CONCISE_MEMBERS) {
                        popupMenu.inflate(R.menu.popup_menu_sort_members)
                        popupMenu.show()
                        setCurrentSoringChecked(popupMenu, MyApplication.membersSortingKey)
                        setMenuItemClickListener(
                            popupMenu = popupMenu,
                            queryType = QuerySupplier.QueryType.CONCISE_MEMBERS,
                            sortingKey = MyApplication.membersSortingKey
                        )

                    } else if (getQueryType() == QuerySupplier.QueryType.FEES) {
                        popupMenu.inflate(R.menu.popup_menu_sort_fees)
                        popupMenu.show()
                        setCurrentSoringChecked(popupMenu, MyApplication.feesSortingKey)
                        setMenuItemClickListener(
                            popupMenu = popupMenu,
                            queryType = QuerySupplier.QueryType.FEES,
                            sortingKey = MyApplication.feesSortingKey
                        )
                    }
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

        val viewPagerAdapter = MainFragmentViewPagerAdapter(this)
        binding.viewpager2.adapter = viewPagerAdapter
        TabLayoutMediator(binding.viewpager2Tabs, binding.viewpager2) { tab, position ->
            tab.text = getTitleForPosition(position)
        }.attach()
    }

    private fun saveSortingToPreferences(key: Preferences.Key<Int>, value: Int) {
        lifecycleScope.launch {
            MyApplication.dataStore.edit { settings ->
                settings[key] = value
            }
        }
    }

    private suspend fun readSortingFromPreferences(sortingKey: Preferences.Key<Int>): Int? {
        return withContext(lifecycleScope.coroutineContext + Dispatchers.IO) {
            val preferences = MyApplication.dataStore.data.first()
            preferences[sortingKey]
        }
    }

    private fun setMenuItemClickListener(
        popupMenu: PopupMenu,
        queryType: QuerySupplier.QueryType,
        sortingKey: Preferences.Key<Int>
    ) {
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sort_by_name -> { saveSortingToPreferences(sortingKey, 0);
                    QuerySupplier.newSortQuery(queryType, "name")
                }
                R.id.sort_by_fees -> { saveSortingToPreferences(sortingKey, 1);
                    QuerySupplier.newSortQuery(queryType, "year", "month", "day")
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun setCurrentSoringChecked(popupMenu: PopupMenu, sortingKey: Preferences.Key<Int>) {
        lifecycleScope.launch {
            val currentSorting =
                readSortingFromPreferences(sortingKey)
            if (currentSorting == 0) {
                popupMenu.menu[0].isChecked = true
            } else if (currentSorting == 1) {
                popupMenu.menu[1].isChecked = true;
            }
        }
    }

    private fun getTitleForPosition(position: Int): String {
        return when (position) {
            0 -> "ALL MEMBERS"
            else -> "FEE RECORDS"
        }
    }

    fun getQueryType(): QuerySupplier.QueryType {
        return when (currentPosition) {
            0 -> QuerySupplier.QueryType.CONCISE_MEMBERS
            else -> QuerySupplier.QueryType.FEES
        }
    }

}