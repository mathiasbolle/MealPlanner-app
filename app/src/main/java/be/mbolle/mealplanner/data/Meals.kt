package be.mbolle.mealplanner.data

import be.mbolle.mealplanner.model.Meal
import java.time.LocalDate

private val beginDate = LocalDate.of(2025, 3, 17)

val meals = listOf<Meal>(
    Meal("Vleeskoek met boontjes", date = LocalDate.of(2025, 3, 17)),
    Meal("broccoligratin met schnitzel", date = LocalDate.of(2025, 3, 18)),
    Meal("Spaghetti bolognaise", date = LocalDate.of(2025, 3, 19)),
    Meal("varkensmignonet met wortelpuree", date = LocalDate.of(2025, 3, 20)),
    Meal("SCAMPI", date = LocalDate.of(2025, 3, 21)),
    Meal("PREISCHOTEL", date = LocalDate.of(2025, 3, 22)),
    Meal("biefstuk  met champignonsaus, kwartspatatjes salade", date = LocalDate.of(2025, 3, 23)),

    Meal("macaroni met zalm, spinazie en kaassaus", date = LocalDate.of(2025, 3, 24)),
    Meal("Bloemkool in kaassaus met braadworst en aardappelen", date = LocalDate.of(2025, 3, 25)),
    Meal("wortelpuree met fish stickx", date = LocalDate.of(2025, 3, 26)),
    Meal("kotelet met puree van andijvie ", date = LocalDate.of(2025, 3, 27)),
    Meal("waterzooi", date = LocalDate.of(2025, 3, 28)),
    Meal("vispannetje", date = LocalDate.of(2025, 3, 29)),
    Meal("balletjes in tomatensaus", date = LocalDate.of(2025, 3, 30)),
)