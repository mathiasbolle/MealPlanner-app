package be.mbolle.mealplanner


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.mbolle.mealplanner.data.meals
import be.mbolle.mealplanner.ui.theme.MealPlannerTheme
import be.mbolle.mealplanner.util.getAbbrDay
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields


@Composable
fun MealPlannerApp(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        TimeframeButtons(
            modifier = Modifier.fillMaxWidth(),
            onClickCurrentWeek = {},
            onClickNextWeek = {},
            onClickNextMonth = {}
        )
        Text(
            "Menu 17/03 - 23/03", modifier = Modifier.padding(top= 30.dp, bottom = 10.dp),
            fontSize = 25.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Light
        )
        Menu()
    }
}

@Composable
fun Menu(modifier: Modifier = Modifier) {
    val weekField = WeekFields.of(DayOfWeek.MONDAY, 7)
    val tempWeekBasedOfYear = weekField.weekOfWeekBasedYear()

    val groupedByWeek = meals.groupBy { meal -> meal.date.get(tempWeekBasedOfYear) }.values
    Log.d("MealPlannerApp", groupedByWeek.toString())

    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        groupedByWeek.forEach { mealsPerWeek ->
            Log.d("MealPlannerApp", mealsPerWeek.toString())
            items(mealsPerWeek) { meal ->
                if (meal.date == LocalDate.of(2025, 3, 17)) {
                    Box(modifier = Modifier.padding(bottom = 22.dp)) {
                        Menuitem(
                            meal = meal,
                            onMealAction = {},
                            modifier = modifier.padding(vertical = 10.dp)
                        )
                    }
                } else {
                    Menuitem(
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

@Composable
fun TimeframeButtons(
    modifier: Modifier = Modifier,
    onClickCurrentWeek: () -> Unit,
    onClickNextWeek: () -> Unit,
    onClickNextMonth: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.Start, modifier = modifier) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(7.dp)
                )
                .clickable { onClickCurrentWeek() }
                .padding(horizontal = 10.dp, vertical = 5.dp)

        ) {
            Text("This week")
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(7.dp)
                )
                .clickable { onClickNextWeek() }
                .padding(horizontal = 10.dp, vertical = 5.dp)

        ) {
            Text("Next week")
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .border(
                    2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(7.dp)
                )
                .clickable { onClickNextMonth() }
                .padding(horizontal = 10.dp, vertical = 5.dp)

        ) {
            Text("Next Month")
        }
    }
}


@Composable
fun Menuitem(
    meal: Meal,
    onMealAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (meal.date == LocalDate.of(
            2025,
            3,
            17
        )
    ) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer

    val fontWeight = if (meal.date == LocalDate.of(
            2025,
            3,
            17
    )) FontWeight.Bold else FontWeight.Normal

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
            CalendarDate(
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
            Icon(painter = painterResource(R.drawable.more_horiz), contentDescription = "more")
        }
    }
}

@Composable
fun CalendarDate(modifier: Modifier = Modifier, date: LocalDate) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(7.dp)
            )
            .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .width(45.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                date.dayOfWeek.getAbbrDay(),
                style = TextStyle()
            ) // implicitly set the includeFontPadding to false
            Text(
                "${date.dayOfMonth}",
                fontWeight = FontWeight.Light
            ) //TODO correctly align this  https://developer.android.com/develop/ui/compose/layouts/intrinsic-measurements#intrinsics-in-action
        }
    }
}

@Preview
@Composable
fun CalendarDatePreview() {
    val aMeal = meals.first()
    MealPlannerTheme {
        CalendarDate(date = aMeal.date)
    }
}

@Preview
@Composable
fun MenuItem() {
    val aMeal = meals.first()

    MealPlannerTheme {
        Menuitem(
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
fun MenuItems() {
    MealPlannerTheme {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(meals) { meal ->
                Menuitem(
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