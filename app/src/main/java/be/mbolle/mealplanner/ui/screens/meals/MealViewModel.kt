package be.mbolle.mealplanner.ui.screens.meals

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MealViewModel : ViewModel() {
    var mealState by mutableStateOf(MealState())
    private set


    fun changeMealSection(mealSections: MealSections) {
        mealState = mealState.copy(activeSection = mealSections)
        Log.d("MealViewModel", mealState.toString())
    }
}