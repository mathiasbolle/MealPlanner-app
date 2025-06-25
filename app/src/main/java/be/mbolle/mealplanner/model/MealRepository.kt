package be.mbolle.mealplanner.model

import android.util.Log
import be.mbolle.mealplanner.data.dto.MealDto
import be.mbolle.mealplanner.data.dto.MenuDto
import be.mbolle.mealplanner.data.local.room.entities.MealMenuEntity
import be.mbolle.mealplanner.data.local.room.entities.MenuWithMealMenus

interface MealRepository {
    suspend fun getMenu(): List<Menu>
    suspend fun getMenuById(id: Int): Menu
    suspend fun refreshFoodItems(): List<Meal>
    suspend fun getLocalFoodItems(): List<Meal>
    suspend fun createIngredientFromMeal(meal: Meal)
    suspend fun deleteMenu(id: Int)
    suspend fun switchMenu(id: Int, menu: Menu)
}

fun List<MenuDto>.toMenuModel(): List<Menu> {
    return this.map { meal ->
        val mealString = meal.mealMenuDtos.joinToString { meal -> meal.name }
        Log.d("MealRepository", Menu(meal = mealString, date = meal.date, id = meal.id).toString())
        return@map Menu(meal = mealString, date = meal.date, id = meal.id)
    }
}

fun MenuDto.toMenuModel(): Menu {
    val mealString = mealMenuDtos.joinToString { meal -> meal.name }
    return Menu(
        id = this.id,
        meal = mealString,
        date = this.date
    )
}

fun List<MenuWithMealMenus>.toMenuModelFromDb(): List<Menu> {
    return this.map { meal ->
        val mealString = meal.mealMenu.joinToString { meal -> meal.name }
        return@map Menu(id = meal.menu.menuId, meal = mealString, date = meal.menu.date)
    }
}

fun List<MealMenuEntity>.toMealModelFromDb(): List<Meal> {
    Log.d("MealRepository", this.toString())
    return this.map { mealMenuEntity ->
        return@map Meal(mealMenuEntity.name, mealKind = mealMenuEntity.kind.toMealKind())
    }
}

fun List<MealDto>.toMealModel(): List<Meal> {
    return this.map { meal ->
        return@map Meal(meal.name, mealKind = meal.kind.toMealKind())
    }
}

private fun String.toMealKind(): MealKinds {
    return when (this) {
        "Meals" -> MealKinds.DISH
        "Vlees" -> MealKinds.MEAT
        "Groenten" -> MealKinds.VEGETABLES
        "Saus" -> MealKinds.SAUCE
        "Deegware" -> MealKinds.PASTA_PATATO
        else -> MealKinds.OTHER
    }
}