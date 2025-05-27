package be.mbolle.mealplanner.ui.screens.meals.recipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.mealplanner.ui.composables.CreateMealDialog
import be.mbolle.mealplanner.ui.composables.DateButtons
import be.mbolle.mealplanner.ui.composables.MealPlannerButton
import be.mbolle.mealplanner.ui.composables.menu.MealList

@Composable
fun MealContent(modifier: Modifier = Modifier) {
    val viewmodel: RecipeViewModel = viewModel(factory = RecipeViewModel.Factory)
    val state = viewmodel.recipeState

    Column(modifier = modifier) {
        DateButtons(
            modifier = Modifier.fillMaxWidth(),
            buttons = listOf(
                {
                    MealPlannerButton(isActive = true, text = "Meals") {
                        //TODO specific functionality
                    }
                },
                {
                    MealPlannerButton(
                        isDisabled = true, text = "Exclusion",
                        modifier = Modifier.clickable(enabled = false) {

                        }
                    ) {
                    }
                },
            )
        )

        when (state.recipeStatus) {
            is RecipeStatus.Loading -> {

            }

            is RecipeStatus.Succeed -> {

                Box(modifier = Modifier.fillMaxSize()) {
                    MealList(mealList = state.recipeStatus.list)

                    AddRecipe(modifier = Modifier.align(Alignment.BottomEnd)) {
                        // on click
                        viewmodel.openDialog()
                    }
                    CreateMealDialog(
                        openDialog = state.mealCreation.openDialog,
                        setOpenDialog = { viewmodel.closeDialog() },
                        onCancel = { viewmodel.closeDialog() },
                        name = state.mealCreation.name,
                        setName = { name -> viewmodel.setName(name)},
                        onConfirm = {
                            viewmodel.createRecipe()
                        })
                }
            }

            else -> {
            }
        }
    }
}

@Composable
fun AddRecipe(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}

@Preview
@Composable
fun MealContentPreview() {
    MealContent()
}