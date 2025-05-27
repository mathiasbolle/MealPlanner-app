package be.mbolle.mealplanner.data

import be.mbolle.mealplanner.data.api.IngredientService
import be.mbolle.mealplanner.data.entities.Ingredient
import be.mbolle.mealplanner.model.Meal

interface IngredientRepository {
    suspend fun createIngredientFromMeal(
        meal: Meal
    )
}

class ApiIngredientRepository(val ingredientService: IngredientService): IngredientRepository {
    override suspend fun createIngredientFromMeal(meal: Meal) {
        ingredientService.addIngredient(Ingredient(meal.name, 7))
    }
}