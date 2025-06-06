package be.mbolle.mealplanner.data

import be.mbolle.mealplanner.data.dto.MealDto
import be.mbolle.mealplanner.data.dto.MenuDto
import be.mbolle.mealplanner.model.Meal
import be.mbolle.mealplanner.model.Menu

interface MealRepository {
    suspend fun getMenu(): List<MenuDto>
    suspend fun getMeal(): List<MealDto>
    suspend fun createIngredientFromMeal(meal: Meal)
}
fun List<MenuDto>.toMenuModel(): List<Menu> {
    return this.map { meal ->
        val mealString = meal.mealMenuDtos.joinToString { meal -> meal.name }
        return@map Menu(meal = mealString, date = meal.date, id = meal.id)
    }
}
fun List<MealDto>.toMealModel(): List<Meal> {
    return this.map { meal ->
        return@map Meal(meal.name)
    }
}
