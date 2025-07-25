package be.mbolle.mealplanner.ui.screens.meals.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import be.mbolle.mealplanner.MainApplication
import be.mbolle.mealplanner.model.MealRepository
import be.mbolle.mealplanner.model.Menu
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.temporal.WeekFields

class MenuViewModel(private val mealRepository: MealRepository) : ViewModel() {
    var menuState = MutableStateFlow(
        MenuState()
    )
        private set

    private fun getInitMealDetails(): Deferred<MenuStatus> {
        return viewModelScope.async {
            try {
                val result = mealRepository.getMenu()
                // add mealsByWeek
                return@async MenuStatus.Succeed(list = (result))
            } catch (e: Exception) {
                return@async MenuStatus.Error(e.toString())
            }
        }
    }

    fun choseMenu(menu: Menu) {
        if (menuState.value.mealDetails is MenuStatus.Succeed) {
            (menuState.value.mealDetails as MenuStatus.Succeed).let {
                menuState.update { uiState ->
                    uiState.copy(
                        mealDetails = it.copy(selectedMenu = menu)
                    )
                }
            }
        }
    }

    fun getMenu() {
        viewModelScope.launch {
            Log.d("MealPlannerViewModel", getInitMealDetails().await().toString())
            menuState.update { uiState ->
                Log.d("MenuViewModel", uiState.toString())
                uiState.copy(
                    mealDetails = getInitMealDetails().await()
                )
            }
        }
    }

    fun deleteMenu() {
        viewModelScope.launch {
            if (menuState.value.mealDetails is MenuStatus.Succeed) {
                (menuState.value.mealDetails as MenuStatus.Succeed).let {
                    if (it.selectedMenu != null) {
                        mealRepository.deleteMenu(
                            it.selectedMenu.id
                        )
                    }
                }
            }
        }
        getMenu()
    }

    init {
        Log.d("MenuViewModel", "this part is called!")
        getMenu()
    }

    private suspend fun mealsByWeek(menus: Flow<List<Menu>>): Flow<List<Menu>> {
        val weekField = WeekFields.of(DayOfWeek.MONDAY, 7)
        val tempWeekBasedOfYear = weekField.weekOfWeekBasedYear()

        Log.d("MenuViewModel", menus.toString())


        return menus.toList().flatten()
            .groupBy { meal -> meal.date.get(tempWeekBasedOfYear) }.values.asFlow()

    }

    private fun markAsActive(text: String) {
        val activeScroll = menuState.value.activeScroll.toMutableMap()
        activeScroll.forEach { k, v -> activeScroll[k] = false }
        activeScroll[text] = true

        menuState.update { uiState ->
            uiState.copy(
                activeScroll = activeScroll.toMap()
            )
        }
    }

    fun scrollToCurrentWeek(text: String) {
        menuState.value.copy(scrollIndex = 0)
        markAsActive(text)

    }

    fun scrollToNextWeek(text: String) {
        menuState.value.copy(scrollIndex = 7)
        markAsActive(text)

    }

    fun scrollToNextMonth(text: String) {
        menuState.value.copy(scrollIndex = 0)
        markAsActive(text)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return MenuViewModel(
                    mealRepository = MainApplication.container.remoteMealRepository
                ) as T
            }
        }
    }
}