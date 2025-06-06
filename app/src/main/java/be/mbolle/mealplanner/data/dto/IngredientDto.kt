package be.mbolle.mealplanner.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    val name: String,
    val kind: Int // should be extracted to a enum for better maintainability
)

