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
import com.project.masterapp.PlantRepository.Singleton.plantList
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

        val collectionRecyclerView = view?.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView?.adapter = PlantAdapter(context, plantList, R.layout.item_vertical_plant )
        collectionRecyclerView?.layoutManager= LinearLayoutManager(context)
        return view
    }

}