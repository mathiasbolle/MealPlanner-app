package be.mbolle.mealplanner.ui.screens.meals.menu.replace

import be.mbolle.mealplanner.model.Meal
import be.mbolle.mealplanner.model.Menu

data class ReplaceMenuState(
    val menu: Menu? = null,
    val selectedCategory: ReplaceMenuCategory = ReplaceMenuCategory.CUSTOM,
    val content: ReplaceMenuResult = ReplaceMenuResult.Loading
)

sealed interface ReplaceMenuResult {
    data class Succeed(
        val replaceMenuFormat: ReplaceMenuFormat
    ) : ReplaceMenuResult


    data class Error(val message: String) : ReplaceMenuResult
    object Loading : ReplaceMenuResult
}

sealed interface ReplaceMenuFormat {
    data class Predefined(
        val list: List<Meal> = emptyList(),
        val selectedMeal: Meal? = null,
    ) : ReplaceMenuFormat

    data class Custom(
        val customMap: Map<Int, List<Meal>> = emptyMap(),
        val selectedMeat: Meal? = null,
        val selectedVegetables: Meal? = null,
        val selectedPatatoes: Meal? = null
    ) : ReplaceMenuFormat
}