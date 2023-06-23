package com.project.masterapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.project.masterapp.HomeActivity
import com.project.masterapp.PlantModel
import com.project.masterapp.PlantRepository.Singleton.plantList
import com.project.masterapp.R
import com.project.masterapp.adapter.PlantAdapter
import com.project.masterapp.adapter.PlantItemDecoration

class HomeFragment( private val context: HomeActivity) : Fragment(
) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)


        val horizontalRecyclerView = view?.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView?.adapter = PlantAdapter(context, plantList, R.layout.item_horizontal_plant)

        val verticalRecyclerView = view?.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView?.adapter = PlantAdapter(context, plantList, R.layout.item_vertical_plant)
        verticalRecyclerView?.addItemDecoration(PlantItemDecoration())

        return view
    }
}