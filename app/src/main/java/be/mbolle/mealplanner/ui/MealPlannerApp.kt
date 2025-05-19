package be.mbolle.mealplanner.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.mbolle.mealplanner.data.meals
import be.mbolle.mealplanner.ui.composables.DateIcon
import be.mbolle.mealplanner.ui.screens.menu.MenuScreen
import be.mbolle.mealplanner.ui.theme.MealPlannerTheme

@Composable
fun MealPlannerApp(modifier: Modifier = Modifier) {
    MenuScreen(modifier)
}


@Preview
@Composable
fun CalendarDatePreview() {
    val aMeal = meals.first()
    MealPlannerTheme {
        DateIcon(date = aMeal.date)
    }
}

@Preview
@Composable
fun MealPlannerPreview() {
    MealPlannerTheme {
        MealPlannerApp(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        )
    }
}