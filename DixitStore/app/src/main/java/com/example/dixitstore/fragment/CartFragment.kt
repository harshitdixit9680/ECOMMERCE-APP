package com.example.dixitstore.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.dixitstore.activity.AddressActivity
import com.example.dixitstore.activity.CategoryActivity
import com.example.dixitstore.adapter.CartAdapter
import com.example.dixitstore.databinding.FragmentCartBinding
import com.example.dixitstore.roomdb.AppDatabase
import com.example.dixitstore.roomdb.ProductMoel

class CartFragment : Fragment() {

private lateinit var binding:FragmentCartBinding
private lateinit var list: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        val preferences =requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        var editor = preferences.edit()
        editor.putBoolean("isCart",false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()
        list = ArrayList()


        dao.getAllProducts().observe(requireActivity()){
            binding.cartRecycler.adapter = CartAdapter(requireContext(), it)
            list.clear()
            for (data in it){
                    list.add(data.productId)
                }
            totalCost(it)

        }
        return binding.root
    }

    private fun totalCost(data: List<ProductMoel>?) {
        var total =0
        for (item in data!!){
            total += item.productSp!!.toInt()
            }
        binding.textView12.text = "Total Item In Cart Is ${data.size}"
        binding.textView13.text = "Total Cost : $total"
        binding.checkout.setOnClickListener {
            val intent = Intent(context, AddressActivity::class.java)
            val b = Bundle()
            b.putStringArrayList("productIds",list)
            b.putString("totalCost",total.toString())
            intent.putExtras(b)

            startActivity(intent)
        }
    }

}
