package be.mbolle.mealplanner.ui.screens.meals.menu

import be.mbolle.mealplanner.model.Menu

data class MenuState(
    val mealDetails: MenuStatus = MenuStatus.Loading,
    val scrollIndex: Int = 0,
    val activeScroll: Map<String, Boolean> = mapOf() //immutable
)

sealed interface MenuStatus {
    data class Succeed(
        val list: Collection<List<Menu>>,
        val scrollIndex: Int = 0,
    ) : MenuStatus
    data class Error(val message: String) : MenuStatus
    object Loading : MenuStatus
}