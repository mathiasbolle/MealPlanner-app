package be.mbolle.mealplanner.data

import be.mbolle.mealplanner.data.api.MealService
import be.mbolle.mealplanner.model.Meal

interface MealRepository {
    suspend fun getMeal(): List<be.mbolle.mealplanner.data.entities.Meal>

}

class ApiMealRepository(val mealService: MealService): MealRepository {
    override suspend fun getMeal(): List<be.mbolle.mealplanner.data.entities.Meal> {
        return mealService.getMeal()
    }
}

fun List<be.mbolle.mealplanner.data.entities.Meal>.toMealModel(): List<Meal> {
    return this.map { meal ->
        return@map Meal(meal.name)
    }
}