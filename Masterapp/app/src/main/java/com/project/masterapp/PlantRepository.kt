package com.project.masterapp

import com.bumptech.glide.disklrucache.DiskLruCache.Value
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.masterapp.PlantRepository.Singleton.databaseRef
import com.project.masterapp.PlantRepository.Singleton.plantList

class PlantRepository {

    object Singleton
    {

        // se connecter à la référence plante
        val databaseRef = FirebaseDatabase.getInstance().getReference("plante")

        // créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()

    }

    fun updateData(callback:() -> Unit)
    {
        //absorber les données
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // retirer les anciennes plantes
                plantList.clear()

                // récolter la liste
                for (ds in snapshot.children)
                {
                    //construire un objet plante
                    val plant = ds.getValue(PlantModel::class.java)
                    //vérifier que la plante n'est pas null
                    if(plant != null)
                    {
                        //ajouter la plante à notre liste
                        plantList.add(plant)
                    }
                }
                //actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {
                // si l'élément n'est pas trouvé
            }

        })
    }




}