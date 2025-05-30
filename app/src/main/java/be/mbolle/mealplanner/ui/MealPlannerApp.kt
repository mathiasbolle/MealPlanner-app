package be.mbolle.mealplanner.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.mbolle.mealplanner.R
import be.mbolle.mealplanner.data.entities.menus
import be.mbolle.mealplanner.ui.composables.DateIcon
import be.mbolle.mealplanner.ui.screens.meals.common.MealSections
import be.mbolle.mealplanner.ui.screens.meals.common.MealsScreen
import be.mbolle.mealplanner.ui.theme.MealPlannerTheme

@Composable
fun MealPlannerApp(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier
            .background(
                color =
                    MaterialTheme.colorScheme.primaryContainer
            )
            .statusBarsPadding()
            .systemBarsPadding()
            .safeDrawingPadding()
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { RecipeBottomBar() }
    ) { innerPadding ->
        MealsScreen(innerPadding = innerPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeAppBar(modifier: Modifier = Modifier,
                 menuState: MealSections,
                 changeSection: (MealSections) -> Unit,
                 navigateTo: (MealSections) -> Unit) {
    Box(
        modifier
            .background(
                MaterialTheme.colorScheme.primaryContainer
            )
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row(modifier = modifier) {
            MealSections.entries.forEachIndexed { index, destination ->
                val customColor =
                    if (index == menuState.ordinal) MaterialTheme.colorScheme.secondaryContainer else Color.Unspecified

                Box(
                    Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            color = customColor,
                        )
                        .clickable {
                            changeSection(destination)
                            navigateTo(destination)
                        },
                    contentAlignment = Alignment.Center,
                    propagateMinConstraints = true,
                ) {
                    Icon(
                        painter = painterResource(destination.icon),
                        tint = Color.Black,
                        contentDescription = destination.contentDescription,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 4.dp)
                            .requiredSize(24.dp)

                    )
                }
                if (index + 1 < MealSections.entries.size) {
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }

    }
}

enum class Destination(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int,
    val contentDescription: String,
) {
    MEALS("meals", "Meals", R.drawable.meals, "Meals"),
    INGREDIENTS("ingredients", "Ingredients", R.drawable.ingredients, "Ingredients")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeBottomBar(modifier: Modifier = Modifier) {
    var selectedDestination by rememberSaveable { mutableIntStateOf(Destination.MEALS.ordinal) }

    NavigationBar(
        modifier = modifier,
        windowInsets = NavigationBarDefaults.windowInsets,
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Destination.entries.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = index == selectedDestination,
                onClick = {
                    selectedDestination = index
                },
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = destination.contentDescription,
                        modifier = Modifier.requiredSize(24.dp)
                    )
                },
                label = {
                    Text(destination.label)
                }
            )
        }
    }
}


@Preview
@Composable
fun CalendarDatePreview() {
    val aMeal = menus.first()
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