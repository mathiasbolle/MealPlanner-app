package be.mbolle.mealplanner.ui

import be.mbolle.mealplanner.model.Meal

data class MealPlannerState(
    val mealDetails: MealStatus = MealStatus.Loading,
    val scrollIndex: Int = 0
)

sealed interface MealStatus {
    data class Succeed(val list: Collection<List<Meal>>, val scrollIndex: Int = 0): MealStatus
    data class Error(val message: String): MealStatus
    object Loading: MealStatus
}