package be.mbolle.mealplanner.ui.screens.meals

import be.mbolle.mealplanner.model.Meal

data class MenuState(
    val mealDetails: MenuStatus = MenuStatus.Loading,
    val scrollIndex: Int = 0,
    val activeScroll: Map<String, Boolean> = mapOf() //immutable
)

sealed interface MenuStatus {
    data class Succeed(
        val list: Collection<List<Meal>>,
        val scrollIndex: Int = 0,
    ) : MenuStatus
    data class Error(val message: String) : MenuStatus
    object Loading : MenuStatus
}