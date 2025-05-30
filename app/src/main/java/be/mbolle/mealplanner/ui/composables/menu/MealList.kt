package be.mbolle.mealplanner.ui.composables.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.mbolle.mealplanner.model.Meal
import be.mbolle.mealplanner.ui.composables.MealPlannerList

@Composable
fun MealListItem(meal: Meal, modifier: Modifier = Modifier, onClickOptions: (meal: Meal) -> Unit) {
    MealPlannerList(modifier = modifier, onClickOptions = { onClickOptions(meal) }) {
        Row {
            Spacer(modifier = Modifier.weight(0.25f))
            Column(
                modifier = Modifier
                    .weight(0.75f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    meal.name,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
fun MealList(modifier: Modifier = Modifier, mealList: List<Meal>) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp) ) {
        items(mealList) { meal ->
            MealListItem(
                meal = meal,
                modifier = modifier.padding(vertical = 10.dp),
                onClickOptions = {}
            )
        }
    }
}