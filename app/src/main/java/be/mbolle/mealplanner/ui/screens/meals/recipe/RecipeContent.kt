package be.mbolle.mealplanner.ui.screens.meals.recipe

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.mealplanner.model.Meal
import be.mbolle.mealplanner.model.MealKinds
import be.mbolle.mealplanner.ui.composables.CreateMealDialog
import be.mbolle.mealplanner.ui.composables.DateButtons
import be.mbolle.mealplanner.ui.composables.EditMealPlannerModal
import be.mbolle.mealplanner.ui.composables.MealPlannerButton
import be.mbolle.mealplanner.ui.composables.Option
import be.mbolle.mealplanner.ui.composables.menu.MealList

@Composable
fun MealContent(modifier: Modifier = Modifier,
                viewModel: RecipeViewModel) {
    val state = viewModel.recipeState

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
                var isModalVisible by remember { mutableStateOf(false) } // extract this to vm

                Log.d("RecipeContent", "it is called...")

                Box(modifier = Modifier.fillMaxSize().padding(top = 50.dp)) {
                    MealList(mealList = state.recipeStatus.list, onClickOptions = { meal ->
                        viewModel.choseMeal(meal)
                        isModalVisible = true
                    })

                    AddRecipe(modifier = Modifier.align(Alignment.BottomEnd)) {
                        // on click
                        viewModel.openDialog()
                    }
                    CreateMealDialog(
                        openDialog = state.mealCreation.openDialog,
                        setOpenDialog = { viewModel.closeDialog() },
                        onCancel = { viewModel.closeDialog() },
                        name = state.mealCreation.name,
                        setName = { name -> viewModel.setName(name)},
                        onConfirm = {
                            viewModel.createRecipe()
                        })

                    EditMealPlannerModal(
                        meal = Meal(
                           state.recipeStatus.selectedMeal?.name.toString(),
                            state.recipeStatus.selectedMeal?.mealKind ?: MealKinds.OTHER
                        ),
                        onDismissRequest = { isModalVisible = false },
                        options = listOf(
                            Option(
                                "Rename",
                                {}),
                            Option("Remove",
                                {}),
                        ),
                        isVisible = isModalVisible
                    )
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
    //MealContent()
}