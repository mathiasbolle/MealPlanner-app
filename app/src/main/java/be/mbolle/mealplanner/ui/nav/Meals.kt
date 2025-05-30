package be.mbolle.mealplanner.ui.nav

import be.mbolle.mealplanner.model.Menu
import kotlinx.serialization.Serializable

@Serializable
object Menu

@Serializable
object Meal

@Serializable
data class ReplaceMenu(val menu: Int)