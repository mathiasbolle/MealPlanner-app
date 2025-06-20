package be.mbolle.mealplanner.ui.screens.meals.menu.replace

import be.mbolle.mealplanner.model.Menu

data class SwitchMenuState(
    val fromMenu: Menu,
    val toMenu: Menu?
)