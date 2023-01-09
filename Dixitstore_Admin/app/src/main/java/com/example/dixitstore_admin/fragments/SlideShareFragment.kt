package com.example.dixitstore_admin.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.activity.result.contract.ActivityResultContracts
import com.example.dixitstore_admin.R

import com.example.dixitstore_admin.databinding.FragmentSlideShareBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class SlideShareFragment : Fragment() {
private lateinit var binding : FragmentSlideShareBinding
private var imageUrl : Uri? = null
private lateinit var dialog : Dialog

private var launchGallaryActivity = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
){
    if(it.resultCode == Activity.RESULT_OK){
        imageUrl = it.data!!.data
        binding.imageView.setImageURI(imageUrl)
    }
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSlideShareBinding.inflate(layoutInflater)
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.progress_layout)
        dialog.setCancelable(false)
        binding.apply {
            imageView.setOnClickListener{
                val  intent = Intent("android.intent.action.GET_CONTENT")
                intent.type = "image/*"
                launchGallaryActivity.launch(intent)
            }


                button5.setOnClickListener {
                if(imageUrl != null){
                    uploadImage(imageUrl!!)

                }
                    else{
                    Toast.makeText(requireContext(),"Please Select Image",  Toast.LENGTH_SHORT).show()
                }
                }

        }




        return binding.root
    }

    private fun uploadImage(uri: Uri) {
dialog.show()
        val fileName =  UUID.randomUUID().toString()+".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("slider/$fileName")
        refStorage.putFile(uri)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {
                    image-> storeData(image.toString())
                }
            }

            .addOnFailureListener{
dialog.dismiss()
            }
    }

    private fun storeData(image: String) {
        val db = Firebase.firestore
        val  data = hashMapOf<String,Any>(
            "img" to image
        )

        db.collection("slider").document("item").set(data)
            .addOnSuccessListener {
                dialog.dismiss()
                Toast.makeText(requireContext(), "Slider Updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(requireContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
    }


}