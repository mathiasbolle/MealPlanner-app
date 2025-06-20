package be.mbolle.mealplanner.ui.screens.meals.recipe

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import be.mbolle.mealplanner.ui.screens.meals.common.MealSections
import be.mbolle.mealplanner.ui.screens.meals.common.SubScreenBuilder

@Composable
fun RecipeSubScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(),
    changeSection: (mealSection: MealSections) -> Unit,
    activeSection: MealSections,
    navController: NavHostController,
    viewModel: RecipeViewModel,
) {
    SubScreenBuilder(
        modifier = modifier,
        innerPadding = innerPadding,
        changeSection = changeSection,
        activeSection = activeSection,
        navController = navController,
    ) { navController, modifier ->

        MealContent(
            viewModel = viewModel,
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp),
        )
    }
}
