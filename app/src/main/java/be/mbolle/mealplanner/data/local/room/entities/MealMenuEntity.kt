package be.mbolle.mealplanner.data.local.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["name"],
        unique = true)],

    tableName = "meal_menu")
data class MealMenuEntity(
    @PrimaryKey(autoGenerate = true) val mealMenuId: Long= 0,
    val name: String,
    val kind: String,
)