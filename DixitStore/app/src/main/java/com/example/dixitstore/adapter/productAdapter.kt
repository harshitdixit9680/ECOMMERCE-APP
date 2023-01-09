package com.example.dixitstore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dixitstore.activity.ProductDetailsActivity
import com.example.dixitstore.databinding.LayoutProductItemBinding
import com.example.dixitstore.fragment.AddProductModel

class productAdapter(val context: Context,val list: ArrayList<AddProductModel>) : RecyclerView.Adapter<productAdapter.ProductViewHolder>(){


    inner class ProductViewHolder(val binding :LayoutProductItemBinding)
    : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
      val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView4)
        holder.binding.textView.text = data.productName
        holder.binding.textView3.text = data.productCategory
        holder.binding.textView4.text = data.productMrp
        holder.binding.button.text = data.productSp
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id",list[position].productID)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}