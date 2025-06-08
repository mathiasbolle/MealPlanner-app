package be.mbolle.mealplanner.data.local.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    indices = [Index(value = ["date"],
        unique = true)],
    tableName = "menu"
)
data class MenuEntity(
    @PrimaryKey(autoGenerate = true) val menuId: Int = 0,
    val date: LocalDate,
)