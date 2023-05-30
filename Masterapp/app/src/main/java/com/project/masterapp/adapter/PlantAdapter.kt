package com.project.masterapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.masterapp.HomeActivity
import com.project.masterapp.PlantModel
import com.project.masterapp.R
import org.w3c.dom.Text

class PlantAdapter(private val context:HomeActivity, private val planList: List<PlantModel>, private val layoutId : Int) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    // boite pour ranger tout les composants à contrôler

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName:TextView? = view.findViewById<TextView>(R.id.name_item)
        val plantDescription:TextView? = view.findViewById<TextView>(R.id.description_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = planList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //permet de récupérer les informations de la plante
        val currentPlant = planList[position]

        //utiliser glide pour récupérer l'image à partir de son lien
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        //mettre à jour le nom de la plante
        holder.plantName?.text = currentPlant.name

        //mettre à jour la description de la plante
        holder.plantDescription?.text = currentPlant.description
    }
}