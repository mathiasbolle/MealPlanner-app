package be.mbolle.mealplanner.ui.composables.menu

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.mbolle.mealplanner.R
import be.mbolle.mealplanner.data.meals
import be.mbolle.mealplanner.model.Meal
import be.mbolle.mealplanner.model.isToday
import be.mbolle.mealplanner.ui.composables.DateIcon
import be.mbolle.mealplanner.ui.theme.MealPlannerTheme
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun MenuItem(
    meal: Meal,
    onMealAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
        if (meal.date.isToday()) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer

    // should be derived from a viewmodel, this is presentation logic
    val fontWeight =
        if (meal.date.isToday()) FontWeight.Bold
        else FontWeight.Normal

    Row(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10)
            )
            .height(IntrinsicSize.Min)
            .then(modifier)
    ) {

        Column(
            modifier = Modifier.weight(0.25f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DateIcon(
                date = meal.date,
                modifier = Modifier.padding(5.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(0.85f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                meal.meal,
                fontWeight = fontWeight,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
        }

        Column(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.more_horiz),
                contentDescription = stringResource(
                    R.string.more_icon
                )
            )
        }
    }
}

@Composable
fun Menu(modifier: Modifier = Modifier, mealsState: Collection<List<Meal>>, scrollIndex: Int) {
    when (mealsState) {
        else -> {
            val lazyListState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(key1 = scrollIndex) {
                coroutineScope.launch {
                    lazyListState.scrollToItem(
                        index = scrollIndex
                    )
                }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), state = lazyListState) {
                mealsState.forEach { mealsPerWeek ->
                    Log.d("MealPlannerApp", mealsPerWeek.toString())
                    items(mealsPerWeek) { meal ->
                        if (meal.date == LocalDate.of(2025, 3, 17)) {
                            Box(modifier = Modifier.padding(bottom = 22.dp)) {
                                MenuItem(
                                    meal = meal,
                                    onMealAction = {},
                                    modifier = modifier.padding(vertical = 10.dp)
                                )
                            }
                        } else {
                            MenuItem(
                                meal = meal,
                                onMealAction = {},
                                modifier = modifier.padding(vertical = 10.dp)
                            )

                            if (meal == mealsPerWeek.last()) {
                                // if this is the last meal of the week.
                                Spacer(modifier = Modifier.padding(vertical = 50.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MenuItemPreview() {
    val aMeal = meals.first()

    MealPlannerTheme {
        MenuItem(
            meal = aMeal,
            onMealAction = {},
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clip(shape = RoundedCornerShape(150.dp))
        )

    }
}

@Preview
@Composable
fun MenuItemsPreview() {
    MealPlannerTheme {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(meals) { meal ->
                MenuItem(
                    meal = meal,
                    onMealAction = {},
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(25))
                        .padding(vertical = 10.dp)
                )
            }
        }
    }
}
