package be.mbolle.mealplanner.data

import java.time.LocalDate

fun LocalDate.toStoredString(): String {
    return "${this.year}-${this.month.value}-${this.dayOfMonth}"
}

fun String.toLocalDate(): LocalDate {
    val dateSequence = this.splitToSequence("-").toList()
    val year = dateSequence.first().toInt()
    val month = dateSequence[1].toInt()
    val day = dateSequence.last().toInt()

    return LocalDate.of(year, month, day)
}