package be.mbolle.mealplanner.ui.screens.meals.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import be.mbolle.mealplanner.ui.nav.RecipeAppBar

@Composable
fun SubScreenBuilder(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(),
    changeSection: (mealSection: MealSections) -> Unit,
    activeSection: MealSections,
    navController: NavHostController,
    content: @Composable (navController: NavHostController, modifier: Modifier) -> Unit
) {
    Column {
        RecipeAppBar(
            menuState = activeSection,
            changeSection = { changeSection(it) },
            navigateTo = { mealSection -> navController.navigateTo(mealSection) },
            modifier = modifier
                .fillMaxWidth()
        )

        content(
            navController,
            Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp, top = 20.dp),
        )
    }
}
