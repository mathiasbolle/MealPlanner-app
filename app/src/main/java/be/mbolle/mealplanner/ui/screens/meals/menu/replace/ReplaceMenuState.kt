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
//        val list: List<Meal> = emptyList(),
//        val mealCategory: List<MealKinds>
        val replaceMenuFormat: ReplaceMenuFormat
    ) : ReplaceMenuResult


    data class Error(val message: String) : ReplaceMenuResult
    object Loading : ReplaceMenuResult
}

sealed interface ReplaceMenuFormat {
    data class Predefined(
        val list: List<Meal> = emptyList(),
    ): ReplaceMenuFormat

    data class Custom(
        val customMap: Map<Int, List<Meal>> = emptyMap()
    ): ReplaceMenuFormat
}