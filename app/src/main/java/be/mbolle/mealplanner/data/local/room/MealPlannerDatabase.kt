package be.mbolle.mealplanner.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import be.mbolle.mealplanner.data.local.room.dao.MealMenuDao
import be.mbolle.mealplanner.data.local.room.dao.MenuDao
import be.mbolle.mealplanner.data.local.room.dao.MenuWithMealMenusDao
import be.mbolle.mealplanner.data.local.room.entities.converters.LocalDateConverter
import be.mbolle.mealplanner.data.local.room.entities.MealMenuEntity
import be.mbolle.mealplanner.data.local.room.entities.MenuEntity
import be.mbolle.mealplanner.data.local.room.entities.MenuMealMenuCrossRef

@Database(entities = [MealMenuEntity::class, MenuEntity::class, MenuMealMenuCrossRef::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class MealPlannerDatabase : RoomDatabase() {
    abstract fun getMealMenuDao(): MealMenuDao
    abstract fun getMenuDao(): MenuDao
    abstract fun getMenuWithMealMenuDao(): MenuWithMealMenusDao

    companion object {
        @Volatile
        private var INSTANCE: MealPlannerDatabase? = null

        fun getDatabase(context: Context): MealPlannerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MealPlannerDatabase::class.java,
                    "mealplanner_db"
                )
                    .addTypeConverter(LocalDateConverter())
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

}