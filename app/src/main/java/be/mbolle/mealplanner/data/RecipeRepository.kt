package be.mbolle.mealplanner.data

import be.mbolle.mealplanner.data.api.MenuService
import be.mbolle.mealplanner.model.Menu

interface RecipeRepository {
    suspend fun getMenu(): List<be.mbolle.mealplanner.data.entities.Menu>
}

class ApiRecipeRepository(val recipeApiMenu: MenuService): RecipeRepository {
    override suspend fun getMenu(): List<be.mbolle.mealplanner.data.entities.Menu> {
        return recipeApiMenu.getMenu()
    }
}

fun List<be.mbolle.mealplanner.data.entities.Menu>.toMenuModel(): List<Menu> {
    return this.map { meal ->
        val mealString = meal.mealMenus.joinToString { meal -> meal.name }
        return@map Menu(meal = mealString, date = meal.date)
    }
}