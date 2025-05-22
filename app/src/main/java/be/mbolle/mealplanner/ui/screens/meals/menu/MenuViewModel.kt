package be.mbolle.mealplanner.ui.screens.meals.menu

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import be.mbolle.mealplanner.data.ApiRecipeRepository
import be.mbolle.mealplanner.data.RecipeRepository
import be.mbolle.mealplanner.data.api.ktorHttpClient
import be.mbolle.mealplanner.data.toMenuModel
import be.mbolle.mealplanner.model.Menu
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.temporal.WeekFields

class MenuViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {
    var menuState by mutableStateOf(
        MenuState()
    )
        private set

    private fun getInitMealDetails(): Deferred<MenuStatus> {
        return viewModelScope.async {
            try {
                val result = recipeRepository.getMenu().toMenuModel()
                return@async MenuStatus.Succeed(list = mealsByWeek(result))
            } catch (e: Exception) {
                return@async MenuStatus.Error(e.toString())
            }
        }
    }

    fun getMenu() {
        viewModelScope.launch {
            Log.d("MealPlannerViewModel", getInitMealDetails().await().toString())
            menuState = menuState.copy(
                mealDetails = getInitMealDetails().await()
            )
        }
    }

    init {
        getMenu()
    }

    private fun mealsByWeek(menus: List<Menu>): Collection<List<Menu>> {
        val weekField = WeekFields.of(DayOfWeek.MONDAY, 7)
        val tempWeekBasedOfYear = weekField.weekOfWeekBasedYear()

        return menus.groupBy { meal -> meal.date.get(tempWeekBasedOfYear) }.values
    }

    private fun markAsActive(text: String) {

        val activeScroll = menuState.activeScroll.toMutableMap()
        activeScroll.forEach { k, v -> activeScroll[k] = false }
        activeScroll[text] = true

        menuState = menuState.copy(
            activeScroll = activeScroll.toMap()
        )
    }

    fun scrollToCurrentWeek(text: String) {
        menuState.copy(scrollIndex = 0)
        markAsActive(text)

    }

    fun scrollToNextWeek(text: String) {
        menuState.copy(scrollIndex = 7)
        markAsActive(text)

    }


    fun scrollToNextMonth(text: String) {
        menuState.copy(scrollIndex = 0)
        markAsActive(text)
    }


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {

                //bad practice i know :D

                val recipeMenu = be.mbolle.mealplanner.data.api.Menu(client = ktorHttpClient)
                val recipeRepository = ApiRecipeRepository(recipeMenu)

                return MenuViewModel(
                    recipeRepository = recipeRepository
                ) as T
            }
        }
    }
}