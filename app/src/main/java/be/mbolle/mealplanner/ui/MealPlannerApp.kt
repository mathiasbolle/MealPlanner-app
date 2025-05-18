package be.mbolle.mealplanner.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.mealplanner.R
import be.mbolle.mealplanner.data.meals
import be.mbolle.mealplanner.ui.composables.DateButtons
import be.mbolle.mealplanner.ui.composables.DateIcon
import be.mbolle.mealplanner.ui.composables.menu.Menu
import be.mbolle.mealplanner.ui.theme.MealPlannerTheme

@Composable
fun MealPlannerApp(modifier: Modifier = Modifier) {
    val viewModel: MealPlannerViewModel = viewModel(factory = MealPlannerViewModel.Factory)
    val state = viewModel.mealPlannerState

    Column(modifier = modifier) {
        DateButtons(
            modifier = Modifier.fillMaxWidth(),
            onClickCurrentWeek = {
                viewModel.scrollToCurrentWeek()
            },
            onClickNextWeek = {
                viewModel.scrollToNextWeek()
            },
            onClickNextMonth = {
                viewModel.scrollToNextMonth()
            }
        )
        Text(
            stringResource(R.string.menu_subtitle), modifier = Modifier.padding(top = 30.dp, bottom = 10.dp),
            fontSize = 25.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Light
        )

        when (state.mealDetails) {
            is MealStatus.Succeed -> {
                state.mealDetails.let { details ->
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

@Preview
@Composable
fun CalendarDatePreview() {
    val aMeal = meals.first()
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