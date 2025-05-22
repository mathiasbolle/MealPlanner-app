package be.mbolle.mealplanner.ui.screens.meals.recipe

import be.mbolle.mealplanner.model.Meal


sealed interface RecipeStatus {
    data class Succeed(
        val list:List<Meal> = emptyList(),
    ) : RecipeStatus
    data class Error(val message: String) : RecipeStatus
    object Loading : RecipeStatus
}
