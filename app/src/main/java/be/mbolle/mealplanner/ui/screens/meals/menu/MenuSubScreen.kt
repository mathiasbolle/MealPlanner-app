package be.mbolle.mealplanner.ui.screens.meals.menu

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import be.mbolle.mealplanner.ui.nav.Destination
import be.mbolle.mealplanner.ui.screens.meals.common.MealSections
import be.mbolle.mealplanner.ui.screens.meals.common.SubScreenBuilder

@Composable
fun MenuSubScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(),
    changeSection: (mealSection: MealSections) -> Unit,
    activeSection: MealSections,
    viewModel: MenuViewModel,

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
            viewModel= viewModel,
            modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp),
            onReplaceMenuClick = { menu ->
                Log.d("MenuSubScreen - replace", menu.id.toString())

                navController.navigate(Destination.Meals.ReplaceMenu(menu.id))
            },
            onSwitchMenuClick = { menu ->
                Log.d("MenuSubScreen", menu.id.toString())

                navController.navigate(Destination.Meals.SwitchMenu(menu.id))
            }
        )
    }
}