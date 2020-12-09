package com.example.gymmanager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gymmanager.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)
        binding.addMemberFAB.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAddMemberFragment()
            findNavController().navigate(action)
        }
    }
}