package be.mbolle.mealplanner.ui.screens.meals.common

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import be.mbolle.mealplanner.ui.nav.Meal
import be.mbolle.mealplanner.ui.nav.Menu
import be.mbolle.mealplanner.ui.nav.RecipeAppBar
import be.mbolle.mealplanner.ui.nav.ReplaceMenu
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuStatus
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuSubScreen
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuViewModel
import be.mbolle.mealplanner.ui.screens.meals.menu.replace.MenuItemReplace
import be.mbolle.mealplanner.ui.screens.meals.recipe.RecipeSubScreen

@Composable
fun MealsScreen(modifier: Modifier = Modifier, innerPadding: PaddingValues) {
    val viewModel: MealViewModel = viewModel()

    MealsNavHost(
        modifier = modifier,
        innerPadding = innerPadding,
        viewModel = viewModel
    ) { navigateTo ->
        RecipeAppBar(
            menuState = viewModel.mealState.activeSection,
            changeSection = { mealSection -> viewModel.changeMealSection(mealSection) },
            navigateTo = { mealSection -> navigateTo(mealSection) },
            modifier = modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun MealsNavHost(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    viewModel: MealViewModel,
    recipeAppBar: @Composable (navigateTo: (MealSections) -> Unit) -> Unit
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Menu
    ) {
        composable<Menu> {
            MenuSubScreen(
                innerPadding = innerPadding,
                changeSection = { mealSection -> viewModel.changeMealSection(mealSection) },
                navController = navController,
                activeSection = viewModel.mealState.activeSection,
            )
            BackHandler(true) { }
        }
        composable<Meal> {
            RecipeSubScreen(
                innerPadding = innerPadding,
                changeSection = { mealSection -> viewModel.changeMealSection(mealSection) },
                navController = navController,
                activeSection = viewModel.mealState.activeSection,
            )
            BackHandler(true) { }
        }

        composable<ReplaceMenu> {
            val viewModel: MenuViewModel = viewModel(factory = MenuViewModel.Companion.Factory)
            val state = viewModel.menuState

            val args = it.toRoute<ReplaceMenu>()
            val menuId = args.menu

            Column {
                recipeAppBar { mealSection ->
                    navController.navigateTo(mealSection)
                }
                when (state.mealDetails) {
                    is MenuStatus.Succeed -> {
                        state.mealDetails.let { details ->
                            MenuItemReplace(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .padding(20.dp),
                                initMenu = details.list.first().first(),
                                menuList = details.list
                            )
                        }
                    }

                    is MenuStatus.Error -> {

                    }

                    MenuStatus.Loading -> {

                    }
                }
            }
        }
    }
}

fun NavHostController.navigateTo(mealSections: MealSections) {
    when (mealSections) {
        MealSections.MENU -> this.navigate(Menu)
        MealSections.FRIDGE -> this.navigate(Meal)
    }
}