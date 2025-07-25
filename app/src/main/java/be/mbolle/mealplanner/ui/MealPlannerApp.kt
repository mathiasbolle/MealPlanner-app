package be.mbolle.mealplanner.ui

import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
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
import be.mbolle.mealplanner.ui.screens.ingredients.IngredientViewModel
import be.mbolle.mealplanner.ui.screens.meals.common.MealSections
import be.mbolle.mealplanner.ui.screens.meals.common.MealViewModel
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuStatus
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuSubScreen
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuViewModel
import be.mbolle.mealplanner.ui.screens.meals.menu.replace.MenuItemReplace
import be.mbolle.mealplanner.ui.screens.meals.menu.replace.ReplaceMenuCategory
import be.mbolle.mealplanner.ui.screens.meals.menu.replace.ReplaceMenuViewModel
import be.mbolle.mealplanner.ui.screens.meals.menu.switch.MenuItemSwitch
import be.mbolle.mealplanner.ui.screens.meals.recipe.RecipeSubScreen
import be.mbolle.mealplanner.ui.screens.meals.recipe.RecipeViewModel
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
        MealPlannerNavHost(
            navController = navController,
            innerPadding = innerPadding
        )
    }
}


@Composable
fun MealPlannerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Destination.Meals
    ) {
        navigation<Destination.Meals>(startDestination = Destination.Meals.MenuSub) {

            composable<Destination.Meals.MenuSub> {
                val menuViewModel: MenuViewModel = it.sharedViewModel(
                    navController,
                    MenuViewModel.Companion.Factory
                )
                val viewModel: MealViewModel =
                    it.sharedViewModel(navController)

                MenuSubScreen(
                    viewModel = menuViewModel,
                    innerPadding = innerPadding,
                    changeSection = { mealSection -> viewModel.changeMealSection(mealSection) },
                    navController = navController,
                    activeSection = viewModel.mealState.activeSection,
                )
                BackHandler(true) { }
            }
            composable<Destination.Meals.MealSub> {
                val recipeViewModel: RecipeViewModel = it.sharedViewModel(
                    navController,
                    RecipeViewModel.Factory
                )
                val viewModel: MealViewModel =
                    it.sharedViewModel(navController)

                RecipeSubScreen(
                    viewModel = recipeViewModel,
                    innerPadding = innerPadding,
                    changeSection = { mealSection -> viewModel.changeMealSection(mealSection) },
                    navController = navController,
                    activeSection = viewModel.mealState.activeSection,
                )
                BackHandler(true) { }
            }
            composable<Destination.Meals.SwitchMenu> {
                val viewModel: MealViewModel =
                    it.sharedViewModel(navController)

                val menuViewModel: MenuViewModel =
                    it.sharedViewModel(navController, MenuViewModel.Companion.Factory)
                val state = menuViewModel.menuState.collectAsState()

//                val args = it.toRoute<Destination.Meals.SwitchMenu>()
//                val menu: Int? = it.savedStateHandle.get<Int>("menu")

//                Log.d(
//                    "MealPlannerApp", menu.toString()
//                )

                Column {
                    RecipeAppBar(
                        menuState = viewModel.mealState.activeSection,
                        changeSection = { mealSection -> viewModel.changeMealSection(mealSection) },
                        navigateTo = { mealSection -> navController.navigateTo(mealSection) },
                        modifier = modifier
                            .fillMaxWidth()
                    )
                    when (state.value.mealDetails) {
                        is MenuStatus.Succeed -> {
                            MenuItemSwitch(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .padding(20.dp),
                                menuList = (state.value.mealDetails as MenuStatus.Succeed).list.collectAsState(
                                    initial = emptyList()
                                ).value
                            ) {
                                navController.popBackStack()
                            }
                        }

                        is MenuStatus.Error -> {

                        }

                        MenuStatus.Loading -> {

                        }
                    }
                }
            }

            composable<Destination.Meals.ReplaceMenu> {
                val viewModel: MealViewModel =
                    it.sharedViewModel(navController)

                val viewmodel: ReplaceMenuViewModel = it.sharedViewModel(
                    navController,
                    ReplaceMenuViewModel.Factory
                )

                val args = it.toRoute<Destination.Meals.ReplaceMenu>()
                val menu: Int? = it.savedStateHandle.get<Int>("menu")
                Log.d(
                    "MealPlannerApp - menu", menu.toString()
                )

                Column {
                    RecipeAppBar(
                        menuState = viewModel.mealState.activeSection,
                        changeSection = { mealSection -> viewModel.changeMealSection(mealSection) },
                        navigateTo = { mealSection -> navController.navigateTo(mealSection) },
                        modifier = modifier
                            .fillMaxWidth()
                    )

                    MenuItemReplace(
                        modifier = modifier
                            .padding(innerPadding)
                            .padding(20.dp),

                        navigateReplaceMenuCategory = { replaceMenuCategory ->
                            when (replaceMenuCategory) {
                                ReplaceMenuCategory.CUSTOM -> navController
                                    .navigate(Destination.Meals.ReplaceMenu.CustomReplaceMenu)

                                ReplaceMenuCategory.PREDEFINED -> navController
                                    .navigate(Destination.Meals.ReplaceMenu.PredefinedReplaceMenu)
                            }
                        }
                    ) {
                        navController.popBackStack()
                    }
                }
            }
        }

        navigation<Destination.Ingredients>(startDestination = Destination.Ingredients.IngredientList) {
            composable<Destination.Ingredients.IngredientList> {
                val ingredientViewModel: IngredientViewModel = it.sharedViewModel(
                    navController,
                    IngredientViewModel.Companion.Factory
                )
                IngredientScreen(
                    viewModel = ingredientViewModel,
                    modifier = Modifier.padding(all = 20.dp)
                )
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


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
    factory: ViewModelProvider.Factory? = null
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return viewModel(parentEntry, factory = factory)
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