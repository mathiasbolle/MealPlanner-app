package be.mbolle.mealplanner.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    val id: Int,
    val name: String
)
