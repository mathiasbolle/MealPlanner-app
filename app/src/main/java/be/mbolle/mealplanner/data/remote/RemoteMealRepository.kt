package be.mbolle.mealplanner.data.remote

import be.mbolle.mealplanner.data.MealRepository
import be.mbolle.mealplanner.data.dto.IngredientDto
import be.mbolle.mealplanner.data.dto.MealDto
import be.mbolle.mealplanner.data.dto.MenuDto
import be.mbolle.mealplanner.data.remote.api.IngredientService
import be.mbolle.mealplanner.data.remote.api.MealService
import be.mbolle.mealplanner.data.remote.api.MenuService
import be.mbolle.mealplanner.model.Meal

class RemoteMealRepository(
    private val menuService: MenuService,
    private val mealService: MealService,
    private val ingredientService: IngredientService
) : MealRepository {

    override suspend fun getMenu(): List<MenuDto> {
        return menuService.getMenu()
    }

    override suspend fun getMeal(): List<MealDto> {
        return mealService.getMeal()
    }

    override suspend fun createIngredientFromMeal(meal: Meal) {
        return ingredientService.addIngredient(IngredientDto(meal.name, 7))
    }
}