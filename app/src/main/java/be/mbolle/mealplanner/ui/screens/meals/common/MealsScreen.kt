package be.mbolle.mealplanner.ui.screens.meals.common

import android.util.Log
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
import androidx.navigation.navigation
import androidx.navigation.toRoute
import be.mbolle.mealplanner.ui.RecipeAppBar
import be.mbolle.mealplanner.ui.nav.Meal
import be.mbolle.mealplanner.ui.nav.Menu
import be.mbolle.mealplanner.ui.nav.ReplaceMenu
import be.mbolle.mealplanner.ui.screens.meals.recipe.MealContent
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuContent
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuStatus
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuViewModel
import be.mbolle.mealplanner.ui.screens.meals.menu.replace.MenuItemReplace

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


    NavHost(navController = navController, modifier = modifier, startDestination = Menu) {
        composable<Menu> {
            Column {
                recipeAppBar { mealSection ->
                    when (mealSection) {
                        MealSections.MENU -> {
                            navController.navigate(route = Menu)
                        }

                        MealSections.FRIDGE -> {
                            navController.navigate(route = Meal)
                            Log.d("MealsScreen", "triggered...")
                        }
                    }
                }

                MenuContent(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                ) { menu ->
                    //navigate to the right submenu
                    navController.navigate(ReplaceMenu(1)) // mock to replace menu 1
                }
            }
            BackHandler(true) { }
        }
        composable<Meal> {
            Column {
                RecipeAppBar(
                    menuState = viewModel.mealState.activeSection,
                    changeSection = { mealSection -> viewModel.changeMealSection(mealSection) },
                    navigateTo = { mealSection ->
                        when (mealSection) {
                            MealSections.MENU -> {
                                navController.navigate(route = Menu)
                            }

                            MealSections.FRIDGE -> {
                                navController.navigate(route = Meal)
                                Log.d("MealsScreen", "triggered...")
                            }
                        }
                    },
                    modifier = modifier
                        .fillMaxWidth()
                )
                MealContent(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(20.dp),
                )
            }
            BackHandler(true) { }
        }

        composable<ReplaceMenu> {
            val viewModel: MenuViewModel = viewModel(factory = MenuViewModel.Companion.Factory)
            val state = viewModel.menuState

            val args = it.toRoute<ReplaceMenu>()
            val menuId = args.menu

            Column {
                recipeAppBar { mealSection ->
                    when (mealSection) {
                        MealSections.MENU -> {
                            navController.navigate(route = Menu)
                        }

                        MealSections.FRIDGE -> {
                            navController.navigate(route = Meal)
                            Log.d("MealsScreen", "triggered...")
                        }
                    }
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
