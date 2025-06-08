package be.mbolle.mealplanner.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import be.mbolle.mealplanner.data.local.room.entities.MenuEntity
import be.mbolle.mealplanner.data.local.room.entities.MenuMealMenuCrossRef

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu")
    suspend fun getAllMenus(): List<MenuEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertMenu(menu: MenuEntity): Long

    @Insert(onConflict = REPLACE)
    suspend fun insertMenuWithMealMenu(menuMealMenuCrossRef: MenuMealMenuCrossRef): Long
}