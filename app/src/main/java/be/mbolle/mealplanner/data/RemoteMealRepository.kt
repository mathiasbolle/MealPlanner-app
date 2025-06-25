package be.mbolle.mealplanner.data

import android.util.Log
import be.mbolle.mealplanner.data.dto.IngredientDto
import be.mbolle.mealplanner.data.dto.MenuDateDto
import be.mbolle.mealplanner.data.local.room.MealPlannerDatabase
import be.mbolle.mealplanner.data.local.room.dao.toModel
import be.mbolle.mealplanner.data.local.room.entities.MealMenuEntity
import be.mbolle.mealplanner.data.local.room.entities.MenuEntity
import be.mbolle.mealplanner.data.local.room.entities.MenuMealMenuCrossRef
import be.mbolle.mealplanner.data.remote.api.IngredientService
import be.mbolle.mealplanner.data.remote.api.MealService
import be.mbolle.mealplanner.data.remote.api.MenuService
import be.mbolle.mealplanner.model.Meal
import be.mbolle.mealplanner.model.MealRepository
import be.mbolle.mealplanner.model.Menu
import be.mbolle.mealplanner.model.toMealModelFromDb
import be.mbolle.mealplanner.model.toMenuModelFromDb

class RemoteMealRepository(
    private val menuService: MenuService,
    private val mealService: MealService,
    private val ingredientService: IngredientService,
    private val mealPlannerDatabase: MealPlannerDatabase,
) : MealRepository {
    private val menuMealMenuDao = mealPlannerDatabase.getMenuWithMealMenuDao()

    override suspend fun getMenu(): List<Menu> {
        val menuDao = mealPlannerDatabase.getMenuDao()
        val menuMealMenuDao = mealPlannerDatabase.getMenuWithMealMenuDao()
        val mealMenuDao = mealPlannerDatabase.getMealMenuDao()

        val cachedMenus = menuMealMenuDao.getMenuWithMealMenus().toMenuModelFromDb()

        try {
            val menus = menuService.getMenu()
            menus.forEach { menu ->
                val insertedMenuId = menuDao.insertMenu(MenuEntity(date = menu.date, menuId = menu.id))

                menu.mealMenuDtos.forEach { mealMenu ->
                    val insertedMealMenuId =
                        mealMenuDao.insertMealMenu(
                            MealMenuEntity(
                                mealMenuId = mealMenu.id,
                                name = mealMenu.name,
                                kind = mealMenu.kind
                            )
                        )

                    menuDao.insertMenuWithMealMenu(
                        MenuMealMenuCrossRef(
                            insertedMealMenuId,
                            insertedMenuId
                        )
                    )
                }
            }
            return cachedMenus
        } catch (_: Exception) {
            Log.d("RemoteMealRepository", cachedMenus.toString())
            return cachedMenus
        }
    }

    override suspend fun getMenuById(id: Int): Menu {
        return menuMealMenuDao.getMenuWithMealMenuById(id).toModel()
    }

    override suspend fun refreshFoodItems(): List<Meal> {
        val mealMenuDao = mealPlannerDatabase.getMealMenuDao()
        return try {
            val meals = mealService.getMeal()

            meals.forEach { meal ->
                mealMenuDao.insertMealMenu(
                    MealMenuEntity(
                        name = meal.name,
                        kind = meal.kind,
                        mealMenuId = meal.id
                    )
                )
            }

            return mealMenuDao.getAll().toMealModelFromDb()
        } catch (e: Exception) {
            mealMenuDao.getAll().toMealModelFromDb()
        }
    }


    override suspend fun getLocalFoodItems(): List<Meal> {
        val mealMenuDao = mealPlannerDatabase.getMealMenuDao()
        return mealMenuDao.getAll().toMealModelFromDb()
    }

    override suspend fun createIngredientFromMeal(meal: Meal) {
        return ingredientService.addIngredient(IngredientDto(meal.name, 7))
    }

    override suspend fun deleteMenu(id: Int) {
        return menuService.removeMenu(id)
    }

    override suspend fun switchMenu(id: Int, menu: Menu) {
        menuService.swapMenu(id, MenuDateDto(menu.id, menu.date))
    }
}