package com.project.masterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.masterapp.fragments.AddPlantFragment
import com.project.masterapp.fragments.CollectionFragment
import com.project.masterapp.fragments.HomeFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        loadFragment(HomeFragment(this), R.string.home_page_title)

        // importer bottom nav
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true

                }

                R.id.plant_page -> {
                    loadFragment(CollectionFragment(this), R.string.mes_plantes_page_title)
                    return@setOnNavigationItemSelectedListener true

                }

                R.id.add_plant_page -> {

                    loadFragment(AddPlantFragment(this), R.string.add_plant_page_title)
                    return@setOnNavigationItemSelectedListener true

                }
                else -> false
            }
        }



    }
private fun loadFragment(fragment : Fragment, string : Int)
{
    val repo = PlantRepository()
    repo.updateData{
        // actualiser le titre de la page
        findViewById<TextView>(R.id.page_title).text = resources.getString(string)
        //
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}

}
