package be.mbolle.mealplanner.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val name: String,
    val kind: Int // should be extracted to a enum for better maintainability
)

enum class Kind {

}
