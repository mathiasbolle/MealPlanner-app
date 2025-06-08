package be.mbolle.mealplanner.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.mbolle.mealplanner.data.local.room.entities.MealMenuEntity

@Dao
interface MealMenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealMenu(mealMenu: MealMenuEntity): Long

    @Query("SELECT * FROM meal_menu")
    suspend fun getAll(): List<MealMenuEntity>
}