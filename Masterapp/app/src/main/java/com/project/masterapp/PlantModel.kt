package com.project.masterapp

class PlantModel(
    val id: String="1",
    val name: String  = "Tulipe",
    val description :String = "Petite description",
    val imageUrl: String = "https://unsplash.com/fr/photos/_6rR_iP06p4",
    val watered: Boolean = false,
    val humidityLevel : String = if (watered) "Bonne" else "Basse")
