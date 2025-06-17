package be.mbolle.mealplanner.data.local.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["name"],
        unique = true)],

    tableName = "meal_menu")
data class MealMenuEntity(
    @PrimaryKey(autoGenerate = false) val mealMenuId: Int,
    val name: String,
    val kind: String,
)