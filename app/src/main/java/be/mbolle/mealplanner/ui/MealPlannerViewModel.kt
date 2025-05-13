package be.mbolle.mealplanner.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import be.mbolle.mealplanner.Meal
import be.mbolle.mealplanner.data.ApiRecipeRepository
import be.mbolle.mealplanner.data.Menu
import be.mbolle.mealplanner.data.RecipeRepository
import be.mbolle.mealplanner.data.ktorHttpClient
import be.mbolle.mealplanner.data.toMealModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.DayOfWeek
import java.time.temporal.WeekFields

class MealPlannerViewModel(val recipeRepository: RecipeRepository) : ViewModel() {
    var mealPlannerState: MealsState by mutableStateOf(
        MealsState.Loading
    )
        private set

    fun getMealPlanner() {
        viewModelScope.launch {
            mealPlannerState = try {
                val result = recipeRepository.getMenu().toMealModel()
                MealsState.Succeed(
                    list = mealsByWeek(result)
                )
            } catch (e: IOException) {
                MealsState.Error(e.message!!)
            }
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

        if (mealPlannerState is MealsState.Succeed) {
            mealPlannerState = (mealPlannerState as MealsState.Succeed).copy(
                scrollIndex = 7
            )
        }
    }

    fun scrollToCurrentWeek() {
        if (mealPlannerState is MealsState.Succeed) {
            mealPlannerState = (mealPlannerState as MealsState.Succeed).copy(
                scrollIndex = 0
            )
        }
    }

    fun scrollToNextMonth() {
        if (mealPlannerState is MealsState.Succeed) {
            mealPlannerState = (mealPlannerState as MealsState.Succeed).copy(
                scrollIndex = 0
            )
        }
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
