package com.example.dixitstore.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.dixitstore.R
import com.example.dixitstore.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button4.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
        binding.button3.setOnClickListener {
            if(binding.userNumber.text!!.isEmpty())
                Toast.makeText(this, "Please Provide Number", Toast.LENGTH_SHORT).show()
            else
                sendOTP(binding.userNumber.text.toString())
        }

    }
private lateinit var builder:AlertDialog

    private fun sendOTP(number: String) {
          builder = AlertDialog.Builder(this)
            .setTitle("Loading.....")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        builder.show()


        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$number")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        }

        override fun onVerificationFailed(e: FirebaseException) {


        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            builder.dismiss()
          val intent = Intent(this@LoginActivity,OTPActivity::class.java)
            intent.putExtra("verificationId",verificationId)
            intent.putExtra("number",binding.userNumber.text.toString())
            startActivity(intent)
        }
    }




}