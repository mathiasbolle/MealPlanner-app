package be.mbolle.mealplanner.ui.screens.meals.menu

import be.mbolle.mealplanner.model.Menu
import kotlinx.coroutines.flow.Flow

data class MenuState(
    val mealDetails: MenuStatus = MenuStatus.Loading,
    val scrollIndex: Int = 0,
    val activeScroll: Map<String, Boolean> = mapOf() //immutable
)

sealed interface MenuStatus {
    data class Succeed(
        val list: Flow<List<Menu>>,
        val scrollIndex: Int = 0,
        val selectedMenu: Menu? = null
    ) : MenuStatus
    data class Error(val message: String) : MenuStatus
    object Loading : MenuStatus
}