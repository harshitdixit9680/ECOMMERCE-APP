package com.example.dixitstore.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.getCategory
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.dixitstore.R
import com.example.dixitstore.activity.ProductDetailsActivity
import com.example.dixitstore.adapter.productAdapter
import com.example.dixitstore.databinding.FragmentHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

private lateinit var binding :FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     binding = FragmentHomeBinding.inflate(layoutInflater)
//        binding.button2.setOnClickListener{
//            val intent = Intent(requireContext(),ProductDetailsActivity::class.java)
//            startActivity(intent)
//        }
        val preferences =requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)

        if(preferences.getBoolean("isCart",false))
            findNavController().navigate(R.id.action_homeFragment2_to_cartFragment2)
        getCategory()
        getProducts()
        getSliderImage()
        return binding.root

    }

    private fun getSliderImage() {
        Firebase.firestore.collection("slider").document("item").get().addOnSuccessListener {
            Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImage)
        }
    }

    private fun getProducts() {
        var list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                binding.productRecycler.adapter = productAdapter(requireContext(),list)
            }
    }

    private fun getCategory() {
        var list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.categoryRecycler.adapter = CategoryAdapter(requireContext(),list)
            }
    }

}