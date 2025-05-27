package be.mbolle.mealplanner.ui.screens.meals.recipe

import be.mbolle.mealplanner.model.Meal


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
    ) : RecipeStatus
    data class Error(val message: String) : RecipeStatus
    object Loading : RecipeStatus
}
