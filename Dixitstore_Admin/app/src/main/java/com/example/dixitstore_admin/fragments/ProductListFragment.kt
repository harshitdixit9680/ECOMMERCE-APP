package com.example.dixitstore_admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.dixitstore_admin.R
import com.example.dixitstore_admin.databinding.FragmentProductListBinding


class ProductListFragment : Fragment() {

private lateinit var binding :FragmentProductListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(layoutInflater)
        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_productListFragment2_to_addProductFragment2)
        }
        return binding.root
    }

}