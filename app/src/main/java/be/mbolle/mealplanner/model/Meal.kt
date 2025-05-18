package be.mbolle.mealplanner.model

import java.time.LocalDate

data class Meal(
    val meal: String,
    val date: LocalDate
    )

fun LocalDate.isToday(): Boolean {
    val time = LocalDate.now()

    return (this == time)
}