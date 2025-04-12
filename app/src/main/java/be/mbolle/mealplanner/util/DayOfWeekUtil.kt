package be.mbolle.mealplanner.util

import java.time.DayOfWeek

fun DayOfWeek.getAbbrDay(): String {
    return when(this) {
        DayOfWeek.MONDAY -> "Ma"
        DayOfWeek.TUESDAY -> "Di"
        DayOfWeek.WEDNESDAY -> "Woe"
        DayOfWeek.THURSDAY -> "Do"
        DayOfWeek.FRIDAY -> "Vrij"
        DayOfWeek.SATURDAY -> "Zat"
        DayOfWeek.SUNDAY -> "Zon"
    }
}