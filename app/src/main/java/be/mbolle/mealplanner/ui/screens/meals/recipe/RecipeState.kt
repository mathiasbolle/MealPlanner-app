package be.mbolle.mealplanner.ui.screens.meals.recipe

import be.mbolle.mealplanner.model.Meal
import be.mbolle.mealplanner.model.Menu


data class RecipeState(
    val recipeStatus: RecipeStatus = RecipeStatus.Loading,
    val mealCreation: MealCreation = MealCreation()
)

data class MealCreation(
    val name: String = "",
    val openDialog: Boolean = false
)

sealed interface RecipeStatus {
    data class Succeed(
        val list:List<Meal> = emptyList(),
        val selectedMeal: Meal? = null
    ) : RecipeStatus
    data class Error(val message: String) : RecipeStatus
    object Loading : RecipeStatus
}
