package be.mbolle.mealplanner.data.local.room.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MenuWithMealMenus(
    @Embedded val menu: MenuEntity,
    @Relation(
        parentColumn = "menuId",
        entityColumn = "mealMenuId",
        associateBy = Junction(MenuMealMenuCrossRef::class)
    )
    val mealMenu: List<MealMenuEntity>
)