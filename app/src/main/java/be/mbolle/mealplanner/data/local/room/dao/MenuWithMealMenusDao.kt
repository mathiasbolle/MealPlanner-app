package be.mbolle.mealplanner.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import be.mbolle.mealplanner.data.local.room.entities.MenuWithMealMenus
import be.mbolle.mealplanner.model.Menu
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuWithMealMenusDao {

    @Query("SELECT * FROM menu")
    fun getMenuWithMealMenus(): Flow<List<MenuWithMealMenus>>

    @Query("SELECT * FROM menu WHERE menuId = :id")
    suspend fun getMenuWithMealMenuById(id: Int): MenuWithMealMenus
}


fun MenuWithMealMenus.toModel(): Menu {
    val mealString = this.mealMenu
        .joinToString { meal -> meal.name }

    return Menu(
        id = this.menu.menuId,
        meal = mealString,
        date = this.menu.date
    )
}