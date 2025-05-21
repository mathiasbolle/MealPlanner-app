package be.mbolle.mealplanner.ui.screens.meals

data class MealState(
    val activeSection: MealSections = MealSections.MENU
)

enum class MealSections {
    MENU, FRIDGE
}
