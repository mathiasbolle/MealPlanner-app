package be.mbolle.mealplanner.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import be.mbolle.mealplanner.data.local.room.entities.MenuWithMealMenus

@Dao
interface MenuWithMealMenusDao {

    @Query("SELECT * FROM menu")
    suspend fun getMenuWithMealMenus(): List<MenuWithMealMenus>
}