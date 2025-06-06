package be.mbolle.mealplanner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.mbolle.mealplanner.data.dto.menus
import be.mbolle.mealplanner.ui.composables.DateIcon
import be.mbolle.mealplanner.ui.nav.RecipeBottomBar
import be.mbolle.mealplanner.ui.screens.meals.common.MealsScreen
import be.mbolle.mealplanner.ui.theme.MealPlannerTheme

@Composable
fun MealPlannerApp(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier
            .background(
                color =
                    MaterialTheme.colorScheme.primaryContainer
            )
            .statusBarsPadding()
            .systemBarsPadding()
            .safeDrawingPadding()
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { RecipeBottomBar() }
    ) { innerPadding ->
        MealsScreen(innerPadding = innerPadding)
    }
}


@Preview
@Composable
fun CalendarDatePreview() {
    val aMeal = menus.first()
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