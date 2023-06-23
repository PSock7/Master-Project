package com.project.masterapp.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.project.masterapp.HomeActivity
import com.project.masterapp.R

class AddPlantFragment(
    private val context : HomeActivity
) :Fragment() {
    private var uploadedImage:ImageView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_plant, container, false)
        uploadedImage = view?.findViewById(R.id.image_preview)
        val pickupImageButton = view?.findViewById<Button>(R.id.upload_image)
        pickupImageButton?.setOnClickListener{pickupImage()}
        return view
    }

    private fun pickupImage(){
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==47 && resultCode== Activity.RESULT_OK)
        {
            if(data == null || data.data==null) return
            val selectedImage = data.data
            uploadedImage?.setImageURI(selectedImage)

        }
    }
}