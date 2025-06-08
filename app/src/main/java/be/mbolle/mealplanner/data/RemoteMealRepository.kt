package be.mbolle.mealplanner.data

import android.util.Log
import be.mbolle.mealplanner.data.dto.IngredientDto
import be.mbolle.mealplanner.data.local.room.MealPlannerDatabase
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
import be.mbolle.mealplanner.model.toMenuModel
import be.mbolle.mealplanner.model.toMenuModelFromDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteMealRepository(
    private val menuService: MenuService,
    private val mealService: MealService,
    private val ingredientService: IngredientService,
    private val mealPlannerDatabase: MealPlannerDatabase,
) : MealRepository {

    override suspend fun getMenu(): List<Menu> {
        val menuDao = mealPlannerDatabase.getMenuDao()
        val menuMealMenuDao = mealPlannerDatabase.getMenuWithMealMenuDao()
        val mealMenuDao = mealPlannerDatabase.getMealMenuDao()

        try {
            val menus = menuService.getMenu()
            menus.forEach { menu ->
                val insertedMenuId = menuDao.insertMenu(MenuEntity(date = menu.date))

                menu.mealMenuDtos.forEach { mealMenu ->
                    val insertedMealMenuId =
                        mealMenuDao.insertMealMenu(
                            MealMenuEntity(
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
            return menus.toMenuModel()
        } catch (e: Exception) {
            val menus = menuMealMenuDao.getMenuWithMealMenus().toMenuModelFromDb()
            Log.d("RemoteMealRepository", menus.toString())
            return menus
        }
    }

    override suspend fun getMeal(): List<Meal> {
        val mealMenuDao = mealPlannerDatabase.getMealMenuDao()
        return try {
            val meals = mealService.getMeal()

            meals.forEach { meal ->
                mealMenuDao.insertMealMenu(
                    MealMenuEntity(
                        name = meal.name,
                        kind = meal.kind
                    )
                )
            }

            return mealMenuDao.getAll().toMealModelFromDb()
        } catch (e: Exception) {
            mealMenuDao.getAll().toMealModelFromDb()
        }
    }

    override suspend fun createIngredientFromMeal(meal: Meal) {
        return ingredientService.addIngredient(IngredientDto(meal.name, 7))
    }

    private suspend fun refreshCache() = withContext(Dispatchers.IO) {
        val menuDao = mealPlannerDatabase.getMenuDao()
        val mealMenuDao = mealPlannerDatabase.getMealMenuDao()

        menuService.getMenu().forEach { menu ->
            val insertedMenuId = menuDao.insertMenu(MenuEntity(date = menu.date))

            menu.mealMenuDtos.forEach { mealMenu ->
//                val insertedMealMenuId =
//                    mealMenuDao.insertMealMenu(MealMenuEntity(name = mealMenu.name))

//                menuDao.insertMenuWithMealMenu(
//                    MenuMealMenuCrossRef(
//                        insertedMealMenuId,
//                        insertedMenuId
//                    )
            }
        }
    }
}