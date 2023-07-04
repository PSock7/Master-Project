package com.project.masterapp

import android.net.Uri
import com.google.android.gms.common.api.internal.TaskUtil
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.project.masterapp.PlantRepository.Singleton.downloadUri
import com.project.masterapp.PlantRepository.Singleton.storageReference
import java.net.URI
import java.util.UUID

class PlantRepository {

    object Singleton {
        // Connection
        private val BUCKET_URL : String = "gs://masterapp-72879.appspot.com"
        val databaseRef = FirebaseDatabase.getInstance("https://masterapp-72879-default-rtdb.firebaseio.com/").getReference("plante")
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)
        var downloadUri : Uri? = null

        // Créer la liste de plante
        val plantList = arrayListOf<PlantModel>()
    }

    fun updateData(callback: () -> Unit) {
        // absorber les données de la BDD dans la liste
        Singleton.databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                // retirer l'ancienne liste
                Singleton.plantList.clear()

                // recolter la liste
                for (ds in snapshot.children){
                    // construire un objet plante
                    val plant = ds.getValue(PlantModel::class.java)

                    // verifier que la plante n'est pas nulle
                    if(plant != null) {
                        Singleton.plantList.add(plant)
                    }
                }
                // actionner le callback
                callback()
            }
        })
    }

    //créer une fonction pour envoyer des fichiers
    fun uploadImage(file: Uri, callback: () -> Unit){
        if(file != null){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>
            { task ->
                if(!task.isSuccessful)
                {
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener{ task ->
                if(task.isSuccessful)
                {
                    downloadUri = task.result
                    callback()
                }
            }
        }

    }

    // MAJ
    fun updatePlant(plant: PlantModel) {
        Singleton.databaseRef.child(plant.id).setValue(plant)
    }

    fun insertPlant(plant: PlantModel) {
        Singleton.databaseRef.child(plant.id).setValue(plant)
    }

    // Delete
    fun deletePlant(plant: PlantModel) {
        Singleton.databaseRef.child(plant.id).removeValue()
    }
}