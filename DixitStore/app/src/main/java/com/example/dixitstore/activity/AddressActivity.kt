package com.example.dixitstore.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dixitstore.R
import com.example.dixitstore.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {
    private lateinit var binding :ActivityAddressBinding
    private lateinit var totalCost : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        totalCost= intent.getStringExtra("totalCost")!!
        loadUserInfo()

        binding.proceed.setOnClickListener {
//            validateData(
//                binding.pinCode.text.toString(),
//                binding.userCity.text.toString(),
//                binding.userState.text.toString(),
//                binding.village.text.toString()
//            )
            val b = Bundle()
            b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
            b.putString("totalCost",totalCost)
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtras(b)
//                intent.putStringArrayListExtra("productIds",intent.getStringArrayExtra("productIds"))
//                 intent.putExtra("totalCost",totalCost)
                startActivity(intent )
        }


    }

//    private fun validateData( pinCode: String,
//                             city: String, state: String, village: String) {
//            if (pinCode.isEmpty() || state.isEmpty() || village.isEmpty()){
//                Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
//            }else{
//                storeData(pinCode,city,state,village)
//            }
//    }

//    private fun storeData(pinCode: String, city: String, state: String, village: String) {
//        val map = hashMapOf<String, Any>()
//        map["village"] = village
//        map["state"] = state
//        map["city"] = city
//        map["pinCode"] = pinCode
//
//        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
//        Firebase.firestore.collection("users")
//            .document(preferences.getString("number","")!!)
//            .update(map).addOnSuccessListener {

//                     val b = Bundle()
//                     b.putStringArrayList("productIds",list)
//                     b.putString("totalCost",total.toString())

//                val intent = Intent(this, CheckoutActivity::class.java)
//                        intent.putExtras(b)
//                intent.putExtra("productIds",intent.getStringArrayExtra("productIds"))
//                  intent.putExtra("totalCost",totalCoast)
//                startActivity(intent )
//
//            }
//
//
//            .addOnFailureListener {
//                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
//            }
//    }

    private fun loadUserInfo() {
        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.village.setText(it.getString("village"))
                binding.userCity.setText(it.getString("city"))
                binding.pinCode.setText(it.getString("pinCode"))
                binding.userState.setText(it.getString("state"))
            }
    }

}