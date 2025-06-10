package be.mbolle.mealplanner.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import be.mbolle.mealplanner.data.dto.menus
import be.mbolle.mealplanner.ui.composables.DateIcon
import be.mbolle.mealplanner.ui.nav.Destination
import be.mbolle.mealplanner.ui.nav.RecipeAppBar
import be.mbolle.mealplanner.ui.nav.RecipeBottomBar
import be.mbolle.mealplanner.ui.screens.ingredients.IngredientScreen
import be.mbolle.mealplanner.ui.screens.meals.common.MealSections
import be.mbolle.mealplanner.ui.screens.meals.common.MealViewModel
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuStatus
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuSubScreen
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuViewModel
import be.mbolle.mealplanner.ui.screens.meals.menu.replace.MenuItemReplace
import be.mbolle.mealplanner.ui.screens.meals.recipe.RecipeSubScreen
import be.mbolle.mealplanner.ui.theme.MealPlannerTheme

@Composable
fun MealPlannerApp(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()

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
        bottomBar = { RecipeBottomBar(navHostController = navController) }
    ) { innerPadding ->
        //MealsScreen(innerPadding = innerPadding)
        val viewModel: MealViewModel = viewModel()
        MealPlannerNavHost(navController = navController, viewModel = viewModel, innerPadding = innerPadding) { navigateTo ->
            RecipeAppBar(
                menuState = viewModel.mealState.activeSection,
                changeSection = { mealSection -> viewModel.changeMealSection(mealSection) },
                navigateTo = { mealSection -> navigateTo(mealSection) },
                modifier = modifier
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
fun MealPlannerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: MealViewModel,
    recipeAppBar: @Composable (navigateTo: (MealSections) -> Unit) -> Unit
) {

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Destination.Meals
    ) {
        navigation<Destination.Meals>(startDestination = Destination.Meals.MenuSub) {
            composable<Destination.Meals.MenuSub> {

                MenuSubScreen(
                    innerPadding = innerPadding,
                    changeSection = { mealSection -> viewModel.changeMealSection(mealSection) },
                    navController = navController,
                    activeSection = viewModel.mealState.activeSection,
                )
                BackHandler(true) { }
            }
            composable<Destination.Meals.MealSub> {
                RecipeSubScreen(
                    innerPadding = innerPadding,
                    changeSection = { mealSection -> viewModel.changeMealSection(mealSection) },
                    navController = navController,
                    activeSection = viewModel.mealState.activeSection,
                )
                BackHandler(true) { }
            }
            composable<Destination.Meals.ReplaceMenu> {

                val viewModel: MenuViewModel = viewModel(factory = MenuViewModel.Companion.Factory)
                val state = viewModel.menuState

                val args = it.toRoute<Destination.Meals.ReplaceMenu>()
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

        navigation<Destination.Ingredients>(startDestination = Destination.Ingredients.IngredientList) {
            composable<Destination.Ingredients.IngredientList> {
                IngredientScreen(modifier = Modifier.padding(all = 20.dp))

            }
        }
    }
}

fun NavHostController.navigateTo(mealSections: MealSections) {
    when (mealSections) {
        MealSections.MENU -> this.navigate(Destination.Meals.MenuSub) // MenuSub
        MealSections.FRIDGE -> this.navigate(Destination.Meals.MealSub) // MealSub
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