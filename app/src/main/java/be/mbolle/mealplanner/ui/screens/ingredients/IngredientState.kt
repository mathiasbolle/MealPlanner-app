package be.mbolle.mealplanner.ui.screens.ingredients

import be.mbolle.mealplanner.model.Meal
import be.mbolle.mealplanner.model.MealKinds

data class IngredientState(
    val ingredientStatus: IngredientStatus = IngredientStatus.Loading,
    val ingredientCreation: IngredientCreation = IngredientCreation()
)

data class IngredientCreation(
    val name: String = "",
    val openDialog: Boolean = false
)


sealed interface IngredientStatus {
    data class Succeed(
        val list: List<Meal> = emptyList(),
        val ingredientCategories: List<MealKinds> = emptyList()
    ) : IngredientStatus

    data class Error(val message: String) : IngredientStatus
    object Loading : IngredientStatus
}