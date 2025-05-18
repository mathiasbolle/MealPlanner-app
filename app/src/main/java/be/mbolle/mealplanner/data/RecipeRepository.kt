package be.mbolle.mealplanner.data

import be.mbolle.mealplanner.model.Meal

interface RecipeRepository {
    suspend fun getMenu(): List<be.mbolle.mealplanner.data.entities.Menu>
}

class ApiRecipeRepository(val recipeApiMenu: Menu): RecipeRepository {
    override suspend fun getMenu(): List<be.mbolle.mealplanner.data.entities.Menu> {
        return recipeApiMenu.getMenu()
    }
}

fun List<be.mbolle.mealplanner.data.entities.Menu>.toMealModel(): List<Meal> {
    return this.map { meal ->
        val mealString = meal.meals.joinToString { meal -> meal.name }
        return@map Meal(meal = mealString, date = meal.date)
    }
}