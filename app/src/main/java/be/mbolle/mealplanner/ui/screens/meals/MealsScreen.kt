package be.mbolle.mealplanner.ui.screens.meals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.mealplanner.ui.RecipeAppBar

@Composable
fun MealsScreen(modifier: Modifier = Modifier, innerPadding: PaddingValues) {
    val viewModel: MealViewModel = viewModel()

    Column {
        RecipeAppBar(
            menuState = viewModel.mealState.activeSection,
            modifier = modifier
                .fillMaxWidth()
        )
        MenuContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp, top = 20.dp),
        )
    }
}