package com.project.masterapp

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.project.masterapp.adapter.PlantAdapter

class PlantPopup(private val adapter : PlantAdapter, private val currentPlant : PlantModel) : Dialog(adapter.context)
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plants_details)
        setupComponent()
        setupCloseButton()
        setupDeleteButton()
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener {
            //fermer la fenêtre
            dismiss()
        }
    }

    private fun setupComponent() {
        // actualiser l'image de la plante
        val plantImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentPlant.imageUrl)).into(plantImage)

        // actualiser le nom de la plante
        findViewById<TextView>(R.id.popup_plant_name).text = currentPlant.name

        //actualiser la description de la plante
        findViewById<TextView>(R.id.popup_plant_description_subtitle).text = currentPlant.description

        //actualiser le niveau d'humidité de la plate
        findViewById<TextView>(R.id.popup_plant_water_subtitle).text = currentPlant.humidityLevel

    }

    private fun setupDeleteButton()
    {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener{
            val repo = PlantRepository()
            repo.deletePlant(currentPlant)
        }
    }
}