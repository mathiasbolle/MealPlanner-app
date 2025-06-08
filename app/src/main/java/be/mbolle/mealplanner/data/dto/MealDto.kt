package be.mbolle.mealplanner.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MealDto(
    val id: Int,
    val name: String,
    val kind: String
)
