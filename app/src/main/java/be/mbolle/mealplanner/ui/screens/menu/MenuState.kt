package be.mbolle.mealplanner.ui.screens.menu

import be.mbolle.mealplanner.model.Meal

data class MenuState(
    val mealDetails: MealStatus = MealStatus.Loading,
    val scrollIndex: Int = 0
)

sealed interface MealStatus {
    data class Succeed(
        val list: Collection<List<Meal>>,
        val scrollIndex: Int = 0,
        val activeScroll: Map<String, Boolean> = mapOf() //immutable
    ) : MealStatus
    data class Error(val message: String) : MealStatus
    object Loading : MealStatus
}