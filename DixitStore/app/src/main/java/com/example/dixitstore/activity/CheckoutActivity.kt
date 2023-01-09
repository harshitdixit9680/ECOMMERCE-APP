package com.example.dixitstore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.dixitstore.MainActivity
import com.example.dixitstore.R
import com.example.dixitstore.roomdb.AppDatabase
import com.example.dixitstore.roomdb.ProductMoel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class CheckoutActivity : AppCompatActivity() , PaymentResultListener
{
    private lateinit var binding : com.example.dixitstore.databinding.ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        val checkout = Checkout()
        checkout.setKeyID("<rzp_test_VFE9ByidBWBC5e>")
        val price = intent.getStringExtra("totalCost")

        try {
            val options =  JSONObject()

            options.put("name", "DIXIT&STORE")
            options.put("description", "Best Ecommerce App")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("order_id", "order_DBJOWzybf0sJbb")//from response of step 3.
            options.put("theme.color", "#6D0AE6")
            options.put("currency", "INR")
            options.put("amount", (price!!.toInt()*100))//pass amount in currency subunits
            options.put("prefill.email", "govinddixit9680a@gmail.com")
            options.put("prefill.contact","8442034230")



            checkout.open(this, options)

        }
        catch(e:Exception ) {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            uploadData()
        }
    }

    private fun uploadData() {
        val id = intent.getStringArrayListExtra("productIds")
        for (currentId in id!!){
            FetchData(currentId)
        }
    }

    private fun FetchData(productId: String?) {
        val dao = AppDatabase.getInstance(this).productDao()
            Firebase.firestore.collection("products")
                .document(productId!!).get().addOnSuccessListener {
                    lifecycleScope.launch(Dispatchers.IO){
                    dao.deleteProduct(ProductMoel(productId))
                    }
                    saveData(it.getString("productName"),
                        it.getString("productSp"),
                        productId

                    )
                }
    }

    private fun saveData(name: String?, price: String?, productId: String) {
        val preference = this.getSharedPreferences("user", MODE_PRIVATE)
        val data= hashMapOf<String,Any>()
        data["name"] = name!!
        data["price"] = price!!
        data["productId"] = productId!!
        data["Status"] = "Ordered"
        data["userId"] = preference.getString("number","")!!
        val firestore = Firebase.firestore.collection("allOrders")
        val key = firestore.document().id
        data["orderId"] = key

        firestore.add(data).addOnSuccessListener {
            Toast.makeText(this, "Ordered Placed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
            .addOnFailureListener {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }


    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Fail", Toast.LENGTH_SHORT).show()
    }
}