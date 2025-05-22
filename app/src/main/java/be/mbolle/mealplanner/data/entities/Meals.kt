package be.mbolle.mealplanner.data.entities

import be.mbolle.mealplanner.model.Menu
import java.time.LocalDate

private val beginDate = LocalDate.of(2025, 3, 17)

val menus = listOf<Menu>(
    Menu("Vleeskoek met boontjes", date = LocalDate.of(2025, 3, 17)),
    Menu("broccoligratin met schnitzel", date = LocalDate.of(2025, 3, 18)),
    Menu("Spaghetti bolognaise", date = LocalDate.of(2025, 3, 19)),
    Menu("varkensmignonet met wortelpuree", date = LocalDate.of(2025, 3, 20)),
    Menu("SCAMPI", date = LocalDate.of(2025, 3, 21)),
    Menu("PREISCHOTEL", date = LocalDate.of(2025, 3, 22)),
    Menu("biefstuk  met champignonsaus, kwartspatatjes salade", date = LocalDate.of(2025, 3, 23)),

    Menu("macaroni met zalm, spinazie en kaassaus", date = LocalDate.of(2025, 3, 24)),
    Menu("Bloemkool in kaassaus met braadworst en aardappelen", date = LocalDate.of(2025, 3, 25)),
    Menu("wortelpuree met fish stickx", date = LocalDate.of(2025, 3, 26)),
    Menu("kotelet met puree van andijvie ", date = LocalDate.of(2025, 3, 27)),
    Menu("waterzooi", date = LocalDate.of(2025, 3, 28)),
    Menu("vispannetje", date = LocalDate.of(2025, 3, 29)),
    Menu("balletjes in tomatensaus", date = LocalDate.of(2025, 3, 30)),
)