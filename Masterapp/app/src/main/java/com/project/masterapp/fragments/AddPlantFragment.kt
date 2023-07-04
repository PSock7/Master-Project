package com.project.masterapp.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.project.masterapp.HomeActivity
import com.project.masterapp.PlantModel
import com.project.masterapp.PlantRepository
import com.project.masterapp.PlantRepository.Singleton.downloadUri
import com.project.masterapp.R
import java.util.UUID

class AddPlantFragment(
    private val context : HomeActivity
) :Fragment() {
    private var uploadedImage:ImageView? = null
    private var file: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_plant, container, false)
        uploadedImage = view?.findViewById(R.id.image_preview)
        val pickupImageButton = view?.findViewById<Button>(R.id.upload_image)
        pickupImageButton?.setOnClickListener{pickupImage()}
        val confirmButton = view?.findViewById<Button>(R.id.confirm_button)
        confirmButton?.setOnClickListener { sendForm(view) }
        return view
    }

    private fun sendForm(view: View) {
        val repo = PlantRepository()
        repo.uploadImage(file!!)
        {
            val plantName = view?.findViewById<EditText>(R.id.name_input)?.text.toString()
            val plantDescrption = view?.findViewById<EditText>(R.id.description_input)?.text.toString()
            val downloadImageUrl = downloadUri

            val plant = PlantModel(
                UUID.randomUUID().toString(),
                plantName,
                plantDescrption,
                downloadImageUrl.toString(),
                watered = true
            )

            repo.insertPlant(plant)
        }
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
            val file = data.data
            uploadedImage?.setImageURI(file)


        }
    }
}