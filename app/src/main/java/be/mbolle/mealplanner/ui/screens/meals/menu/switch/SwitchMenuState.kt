package be.mbolle.mealplanner.ui.screens.meals.menu.switch

import be.mbolle.mealplanner.model.Menu

data class SwitchMenuState(
    val fromMenu: Menu,
    val toMenu: Menu?
)