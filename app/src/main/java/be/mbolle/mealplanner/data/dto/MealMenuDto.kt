package be.mbolle.mealplanner.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class MealMenuDto(
    val name: String,
    val kind: String,
)
