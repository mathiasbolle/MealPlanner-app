package be.mbolle.mealplanner.data.local.room.entities.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import be.mbolle.mealplanner.data.toLocalDate
import be.mbolle.mealplanner.data.toStoredString
import java.time.LocalDate

@ProvidedTypeConverter
class LocalDateConverter {
    @TypeConverter
    fun fromTimestamp(value: String): LocalDate {
        return value.toLocalDate()
    }

    @TypeConverter
    fun localDateToTimestamp(localDate: LocalDate): String {
        return localDate.toStoredString()
    }
}