package be.mbolle.mealplanner.ui.screens.ingredients

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.mbolle.mealplanner.ui.composables.DateButtonsLazyRow
import be.mbolle.mealplanner.ui.composables.MealPlannerButton
import be.mbolle.mealplanner.ui.composables.menu.MealList
import java.util.Locale

@Composable
fun IngredientScreen(
    viewModel: IngredientViewModel,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.ingredientState

    Column(modifier = modifier) {
        when (state.ingredientStatus) {

            is IngredientStatus.Error -> {
                // what to do if it is restored from local database?
            }

            IngredientStatus.Loading -> {

            }

            is IngredientStatus.Succeed -> {

                val mealKindComposable: List<@Composable () -> Unit> =
                    (state.ingredientStatus.ingredientCategories.map { mealKind ->
                        {
                            MealPlannerButton(
                                state.ingredientStatus.selectedCategory == mealKind,
                                mealKind.toString().lowercase(Locale.ROOT)
                            ) {
                                Log.d("IngredientScreen", state.toString())

                                if (state.ingredientStatus.selectedCategory == null || state.ingredientStatus.selectedCategory != mealKind)
                                    viewModel.selectIngredientCategory(mealKind)
                                else if (state.ingredientStatus.selectedCategory == mealKind)
                                    viewModel.unselectIngredientCategory()
                            }
                        }
                    })

                DateButtonsLazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    buttons = mealKindComposable
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp)
                ) {
                    MealList(mealList = state.ingredientStatus.list) {}

                }
            }
        }

    }
}