package be.mbolle.mealplanner.ui.screens.meals.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.mealplanner.R
import be.mbolle.mealplanner.ui.composables.MealPlannerButton
import be.mbolle.mealplanner.ui.composables.DateButtons
import be.mbolle.mealplanner.ui.composables.menu.MenuList

@Composable
fun MenuContent(modifier: Modifier = Modifier) {
    val viewModel: MenuViewModel = viewModel(factory = MenuViewModel.Companion.Factory)
    val state = viewModel.menuState

    val thisWeek = stringResource(R.string.this_week_btn)
    val nextWeek = stringResource(R.string.next_week_btn)
    val nextMonth = stringResource(R.string.next_month_btn)

    Column {
        Column(modifier = modifier) {
            DateButtons(
                modifier = Modifier.fillMaxWidth(),
                buttons = listOf(
                    {
                        MealPlannerButton(state.activeScroll[thisWeek] == true, thisWeek) {
                            viewModel.scrollToCurrentWeek(thisWeek)
                        }
                    },
                    {
                        MealPlannerButton(state.activeScroll[nextWeek] == true, nextWeek) {
                            viewModel.scrollToNextWeek(nextWeek)
                        }
                    },
                    {
                        MealPlannerButton(state.activeScroll[nextMonth] == true, nextMonth) {
                            viewModel.scrollToNextMonth(nextMonth)
                        }
                    },
                ),
            )
            when (state.mealDetails) {
                is MenuStatus.Succeed -> {
                    state.mealDetails.let { details ->

                        Text(
                            stringResource(R.string.menu_subtitle),
                            modifier = Modifier.padding(top = 50.dp, bottom = 10.dp),
                            fontSize = 25.sp,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Light
                        )

                        MenuList(
                            mealsState = details.list,
                            scrollIndex = details.scrollIndex
                        )
                    }
                }

                is MenuStatus.Error -> {

                }
                MenuStatus.Loading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                        Loading()
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun MenuScreenPreview() {
    //MenuContent()
}


@Composable
fun Loading(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.width(64.dp),
        color = MaterialTheme.colorScheme.outline,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}