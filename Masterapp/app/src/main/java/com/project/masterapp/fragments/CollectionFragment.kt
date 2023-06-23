package com.project.masterapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.masterapp.HomeActivity
import com.project.masterapp.PlantModel
import com.project.masterapp.R
import com.project.masterapp.adapter.PlantAdapter
import com.project.masterapp.adapter.PlantItemDecoration

class CollectionFragment(
    private val context: HomeActivity
): Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_collection, container, false)

        //créer une liste qui va stocker ces plantes
        val plantlist = arrayListOf<PlantModel>()

        //enregistrer dans notre liste pissenlit
        plantlist.add(PlantModel("tulipe", "belle plante", "https://cdn.pixabay.com/photo/2018/04/29/18/24/tulip-3360748_1280.jpg", true))
        plantlist.add(PlantModel("rose", "valentin", "https://cdn.pixabay.com/photo/2013/07/21/13/00/rose-165819_1280.jpg", false))
        plantlist.add(PlantModel("cactus", "ça pique", "https://cdn.pixabay.com/photo/2015/04/10/17/03/pots-716579_1280.jpg", true))
        plantlist.add(PlantModel("tournesol", "soleil", "https://cdn.pixabay.com/photo/2016/07/23/00/12/sunflower-1536088_1280.jpg", false))

        val collectionRecyclerView = view?.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView?.adapter = PlantAdapter(context, plantlist, R.layout.item_vertical_plant)
        collectionRecyclerView?.layoutManager= LinearLayoutManager(context)
        return view
    }

}