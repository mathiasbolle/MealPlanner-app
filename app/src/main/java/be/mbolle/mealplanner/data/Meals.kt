package be.mbolle.mealplanner.data

import be.mbolle.mealplanner.Meal
import java.time.LocalDate

private val beginDate = LocalDate.of(2025, 3, 17)

val meals = listOf<Meal>(
    Meal("Vleeskoek met boontjes", date = LocalDate.of(2025, 3, 17)),
    Meal("broccoligratin met schnitzel", date = LocalDate.of(2025, 3, 18)),
    Meal("Spaghetti bolognaise", date = LocalDate.of(2025, 3, 19)),
    Meal("varkensmignonet met wortelpuree", date = LocalDate.of(2025, 3, 20)),
    Meal("SCAMPI", date = LocalDate.of(2025, 3, 21)),
    Meal("PREISCHOTEL", date = LocalDate.of(2025, 3, 22)),
    Meal("biefstuk  met champignonsaus, kwartspatatjes salade", date = LocalDate.of(2025, 3, 23))
)