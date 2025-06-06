package be.mbolle.mealplanner.ui.nav

import kotlinx.serialization.Serializable

@Serializable
object Menu

@Serializable
object Meal

@Serializable
data class ReplaceMenu(val menu: Int)