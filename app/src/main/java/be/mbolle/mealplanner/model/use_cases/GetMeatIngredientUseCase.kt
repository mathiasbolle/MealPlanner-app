package be.mbolle.mealplanner.model.use_cases

import be.mbolle.mealplanner.model.Meal
import be.mbolle.mealplanner.model.MealKinds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMeatIngredientUseCase(
    val ingredientUseCase: GetIngredientsUseCase
) {
    suspend operator fun invoke(): List<Meal> = withContext(Dispatchers.IO) {
        ingredientUseCase().filter { meal -> meal.mealKind == MealKinds.MEAT }
    }
}