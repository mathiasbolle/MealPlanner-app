package be.mbolle.mealplanner.ui.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import be.mbolle.mealplanner.ui.screens.meals.common.MealSections

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