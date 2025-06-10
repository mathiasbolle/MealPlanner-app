package be.mbolle.mealplanner.model.use_cases

import be.mbolle.mealplanner.model.Meal
import be.mbolle.mealplanner.model.MealKinds
import be.mbolle.mealplanner.model.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMealsUseCase(val repository: MealRepository) {
    suspend operator fun invoke(): List<Meal> = withContext(Dispatchers.IO) {
        repository.getFoodItem().filter { meal -> meal.mealKind == MealKinds.DISH }
    }
}