package be.mbolle.mealplanner

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import be.mbolle.mealplanner.data.meals
import java.time.DayOfWeek
import java.time.temporal.WeekFields

class MealPlannerViewModel : ViewModel() {
    val mealPlannerState = mutableStateOf(
        MealPlannerState(
            mealsState = MealsState.Loading,
        )
    )

    init {
        mealPlannerState.value = mealPlannerState.value.copy(
            mealsState = MealsState.Succeed(
                list = mealsByWeek()
            )
        )
    }

    private fun mealsByWeek(): Collection<List<Meal>> {
        val weekField = WeekFields.of(DayOfWeek.MONDAY, 7)
        val tempWeekBasedOfYear = weekField.weekOfWeekBasedYear()

        return meals.groupBy { meal -> meal.date.get(tempWeekBasedOfYear) }.values
    }


    fun scrollToNextWeek() {
        mealPlannerState.value = mealPlannerState.value.copy(
            scrollIndex = 7
        )
    }

    fun scrollToCurrentWeek() {
        mealPlannerState.value = mealPlannerState.value.copy(
            scrollIndex = 0
        )
    }

    fun scrollToNextMonth() {
        mealPlannerState.value = mealPlannerState.value.copy(
            scrollIndex = 0
        )
    }

}