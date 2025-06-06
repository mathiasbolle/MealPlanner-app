package be.mbolle.mealplanner.ui.screens.meals.menu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import be.mbolle.mealplanner.ui.nav.ReplaceMenu
import be.mbolle.mealplanner.ui.screens.meals.common.MealSections
import be.mbolle.mealplanner.ui.screens.meals.common.SubScreenBuilder

@Composable
fun MenuSubScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(),
    changeSection: (mealSection: MealSections) -> Unit,
    activeSection: MealSections,
    navController: NavHostController,
) {
    SubScreenBuilder(
        modifier = modifier,
        innerPadding = innerPadding,
        changeSection = changeSection,
        activeSection = activeSection,
        navController = navController,
    ) { navController, modifier ->
        MenuContent(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp),
        ) { menu ->
            navController.navigate(ReplaceMenu(1)) // mock to replace menu 1
        }
    }
}