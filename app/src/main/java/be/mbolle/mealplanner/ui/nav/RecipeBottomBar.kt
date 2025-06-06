package be.mbolle.mealplanner.ui.nav

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import be.mbolle.mealplanner.R


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
