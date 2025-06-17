package be.mbolle.mealplanner.ui.screens.meals.menu.replace

import be.mbolle.mealplanner.model.Menu

data class ReplaceMenuState(
    val fromMenu: Menu,
    val toMenu: Menu?
)