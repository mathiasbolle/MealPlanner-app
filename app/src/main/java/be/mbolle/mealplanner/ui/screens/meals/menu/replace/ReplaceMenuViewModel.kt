package be.mbolle.mealplanner.ui.screens.meals.menu.replace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ReplaceMenuViewModel: ViewModel() {
    var state by mutableStateOf(ReplaceMenuState())
        private set


}