package be.mbolle.mealplanner.ui.screens.meals.menu.replace

import be.mbolle.mealplanner.model.Menu

data class ReplaceMenuState(
    val menu: Menu? = null,
    val selectedCategory: ReplaceMenuCategory = ReplaceMenuCategory.CUSTOM,
    )