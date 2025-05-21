package be.mbolle.mealplanner.ui.screens.meals

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MealViewModel : ViewModel() {
    val mealState by mutableStateOf(MealState())
}