package be.mbolle.mealplanner.ui.nav

import androidx.annotation.DrawableRes
import be.mbolle.mealplanner.R
import kotlinx.serialization.Serializable

@Serializable
sealed class Destination (
) {
    @Serializable
    object Meals: Destination() {
        @Serializable
        object MenuSub

        @Serializable
        object MealSub

        @Serializable
        data class SwitchMenu(val menu: Int)

        @Serializable
        data class ReplaceMenu(val menu: Int) {
            object CustomReplaceMenu

            object PredefinedReplaceMenu
        }
    }
    @Serializable
    object Ingredients: Destination() {
        @Serializable
        object IngredientList {}
    }
}

enum class BottomNavigation(
    val label: String,
    @DrawableRes val icon: Int,
    val contentDescription: String,
) {
    MEALS("", R.drawable.meals, ""), INGREDIENTS("", R.drawable.ingredients, "")
}

fun Destination.toEnum(): BottomNavigation {
    return when (this) {
        Destination.Ingredients -> BottomNavigation.INGREDIENTS
        Destination.Meals -> BottomNavigation.MEALS
    }
}

fun BottomNavigation.toSealedClass(): Destination {
    return when(this) {
        BottomNavigation.MEALS -> Destination.Meals
        BottomNavigation.INGREDIENTS -> Destination.Ingredients
    }
}

fun Destination.toInt(): Int {
    return when(this) {
        Destination.Meals -> 0
        Destination.Ingredients -> 1
    }
}

fun Destination.values(): List<Int> {
    return listOf(0, 1)
}

@Serializable
object MenuSub

@Serializable
object MealSub

@Serializable
data class ReplaceMenu(val menu: Int)