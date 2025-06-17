package be.mbolle.mealplanner.data.local.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    indices = [Index(
        value = ["date"], // this ensures that the swap functionality works as expected.
        unique = true
    )],
    tableName = "menu"
)
data class MenuEntity(
    @PrimaryKey(autoGenerate = false) val menuId: Int,
    val date: LocalDate,
)