package be.mbolle.mealplanner.ui.screens.meals.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MealsScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues) {
    val viewModel: MealViewModel = viewModel()


}

/*
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
        startDestination = MenuSub
    ) {
        composable<MenuSub> {
            MenuSubScreen(
                innerPadding = innerPadding,
                changeSection = { mealSection -> viewModel.changeMealSection(mealSection) },
                navController = navController,
                activeSection = viewModel.mealState.activeSection,
            )
            BackHandler(true) { }
        }
        composable<MealSub> {
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
        MealSections.MENU -> this.navigate(Destination.Meals.MenuSub) // MenuSub
        MealSections.FRIDGE -> this.navigate(Destination.Meals.MealSub) // MealSub
    }
}

 */