package be.mbolle.mealplanner.ui.screens.meals.menu.replace.subscreen

import be.mbolle.mealplanner.model.Meal

data class ReplaceMenuSubScreenState(
    val meals: List<Meal> = emptyList()
)
