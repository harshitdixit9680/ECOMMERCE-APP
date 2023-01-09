package com.example.dixitstore_admin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dixitstore_admin.Activity.AllOrdersActivity
import com.example.dixitstore_admin.R
import com.example.dixitstore_admin.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentHomeBinding.inflate(layoutInflater)


        binding.button.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment2_to_categoryFragment2)
        }

        binding.button2.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment2_to_productListFragment2)
        }

        binding.button3.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment2_to_slideShareFragment)
        }

        binding.button4.setOnClickListener{
            startActivity(Intent(requireContext(), AllOrdersActivity::class.java))
        }

        return binding.root
    }


}