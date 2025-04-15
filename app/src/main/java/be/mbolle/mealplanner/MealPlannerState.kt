package be.mbolle.mealplanner

data class MealPlannerState(
    val mealsState: MealsState,
    val scrollIndex: Int = 0
)


sealed class MealsState {
    data class Succeed(val list: Collection<List<Meal>>): MealsState()
    data class Error(val message: String): MealsState()
    object Loading: MealsState()
}