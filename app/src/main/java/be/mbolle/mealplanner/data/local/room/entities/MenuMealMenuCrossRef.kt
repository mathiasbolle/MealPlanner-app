package be.mbolle.mealplanner.data.local.room.entities

import androidx.room.Entity

@Entity(primaryKeys = ["mealMenuId", "menuId"], tableName = "menu_details")
data class MenuMealMenuCrossRef(
    val mealMenuId: Long,
    val menuId: Long
)