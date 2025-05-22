package be.mbolle.mealplanner.ui.screens.meals.common
import be.mbolle.mealplanner.R
import androidx.annotation.DrawableRes

data class MealState(
    val activeSection: MealSections = MealSections.MENU
)

enum class MealSections(
    @DrawableRes val icon: Int,
    val contentDescription: String,
) {
    MENU(R.drawable.menu_book, "Menu overview"), FRIDGE(R.drawable.fridge, "Fridge overview")
}
