package be.mbolle.mealplanner.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import be.mbolle.mealplanner.data.ApiRecipeRepository
import be.mbolle.mealplanner.data.Menu
import be.mbolle.mealplanner.data.RecipeRepository
import be.mbolle.mealplanner.data.ktorHttpClient
import be.mbolle.mealplanner.data.toMealModel
import be.mbolle.mealplanner.model.Meal
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.temporal.WeekFields

class MealPlannerViewModel(val recipeRepository: RecipeRepository) : ViewModel() {
    var mealPlannerState by mutableStateOf(
        MealPlannerState()
    )
        private set

    private fun getInitMealDetails(): Deferred<MealStatus> {
        return viewModelScope.async {
            try {
                val result = recipeRepository.getMenu().toMealModel()
                return@async MealStatus.Succeed(list = mealsByWeek(result))
            }catch (e: Exception) {
                return@async MealStatus.Error(e.toString())
            }
        }
    }

    fun getMealPlanner() {
        viewModelScope.launch {
            Log.d("MealPlannerViewModel", getInitMealDetails().await().toString())
            mealPlannerState = mealPlannerState.copy(
                mealDetails = getInitMealDetails().await()
            )
        }
    }

    init {
        getMealPlanner()
    }

    private fun mealsByWeek(meals: List<Meal>): Collection<List<Meal>> {
        val weekField = WeekFields.of(DayOfWeek.MONDAY, 7)
        val tempWeekBasedOfYear = weekField.weekOfWeekBasedYear()

        return meals.groupBy { meal -> meal.date.get(tempWeekBasedOfYear) }.values
    }


    fun scrollToNextWeek() {
        mealPlannerState.copy(scrollIndex = 7)
    }

    fun scrollToCurrentWeek() {
        mealPlannerState.copy(scrollIndex = 0)
    }

    fun scrollToNextMonth() {
        mealPlannerState.copy(scrollIndex = 0)
    }


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {

                //bad practice i know :D

                val recipeMenu = Menu(client = ktorHttpClient)
                val recipeRepository = ApiRecipeRepository(recipeMenu)

                return MealPlannerViewModel(
                    recipeRepository = recipeRepository
                ) as T
            }
        }
    }
}
