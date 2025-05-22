package be.mbolle.mealplanner.ui.screens.meals.recipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.mealplanner.ui.composables.DateButton
import be.mbolle.mealplanner.ui.composables.DateButtons

@Composable
fun MealContent(modifier: Modifier = Modifier) {
    val viewmodel: RecipeViewModel = viewModel(factory = RecipeViewModel.Factory)
    val state = viewmodel.recipeState

    Column(modifier = modifier) {
        DateButtons(
            modifier = Modifier.fillMaxWidth(),
            buttons = listOf(
                {
                    DateButton(isActive = true, text = "Meals") {
                        //TODO specific functionality
                    }
                },
                {
                    DateButton(
                        isDisabled = true, text = "Exclusion",
                        modifier = Modifier.clickable(enabled = false) {

                        }
                    ) {
                    }
                },
            )
        )

        when (state) {
            is RecipeStatus.Loading -> {

            }
            is RecipeStatus.Succeed -> {
                state.let { recipe ->

                    Column {
                        recipe.list.forEach { recipe ->
                            Text(recipe.name)
                        }
                    }
                }
            }
            else -> {
            }
        }
    }
}


@Preview
@Composable
fun MealContentPreview() {
    MealContent()
}