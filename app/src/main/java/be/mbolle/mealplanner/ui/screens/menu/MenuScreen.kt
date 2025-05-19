package be.mbolle.mealplanner.ui.screens.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.mealplanner.R
import be.mbolle.mealplanner.ui.composables.DateButton
import be.mbolle.mealplanner.ui.composables.DateButtons
import be.mbolle.mealplanner.ui.composables.menu.Menu


@Composable
fun MenuScreen(modifier: Modifier = Modifier) {
    val viewModel: MenuViewModel = viewModel(factory = MenuViewModel.Factory)
    val state = viewModel.menuState


    val this_week = stringResource(R.string.this_week_btn)
    val next_week = stringResource(R.string.next_week_btn)
    val next_month = stringResource(R.string.next_month_btn)

    Column(modifier = modifier) {
        when (state.mealDetails) {
            is MealStatus.Succeed -> {
                state.mealDetails.let { details ->
                    DateButtons(
                        modifier = Modifier.fillMaxWidth(),
                        buttons = listOf(
                            {
                                DateButton(details.activeScroll[this_week] == true, this_week) {
                                    viewModel.scrollToCurrentWeek(this_week)
                                }
                            },
                            {
                                DateButton(details.activeScroll[next_week] == true, next_week) {
                                    viewModel.scrollToNextWeek(next_week)
                                }
                            },
                            {
                                DateButton(details.activeScroll[next_month] == true, next_month) {
                                    viewModel.scrollToNextMonth(next_month)
                                }
                            },
                        ),
                    )
                    Text(
                        stringResource(R.string.menu_subtitle),
                        modifier = Modifier.padding(top = 30.dp, bottom = 10.dp),
                        fontSize = 25.sp,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Light
                    )

                    Menu(
                        mealsState = details.list,
                        scrollIndex = details.scrollIndex
                    )
                }
            }

            is MealStatus.Error -> TODO()
            MealStatus.Loading -> {
                Loading()
            }
        }
    }
}


@Composable
fun Loading(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}