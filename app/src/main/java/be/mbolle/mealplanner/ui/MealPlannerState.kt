package be.mbolle.mealplanner.ui

import be.mbolle.mealplanner.Meal

data class MealPlannerState(
    val mealsState: MealsState,
    val scrollIndex: Int = 0
)


sealed class MealsState {
    data class Succeed(val list: Collection<List<Meal>>, val scrollIndex: Int = 0): MealsState()
    data class Error(val message: String): MealsState()
    object Loading: MealsState()
}