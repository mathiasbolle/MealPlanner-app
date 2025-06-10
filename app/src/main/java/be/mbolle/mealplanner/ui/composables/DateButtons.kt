package be.mbolle.mealplanner.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DateButtons(
    modifier: Modifier = Modifier,
    buttons: List<@Composable () -> Unit>
) {
    Row(horizontalArrangement = Arrangement.Start, modifier = modifier) {
        buttons.forEachIndexed { index, value ->
            value()
            if (index + 1 < buttons.size) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Composable
fun DateButtonsLazyRow(
    modifier: Modifier = Modifier,
    buttons: List<@Composable () -> Unit>
) {
    LazyRow(horizontalArrangement = Arrangement.Start, modifier = modifier) {
        itemsIndexed (buttons) { index, value ->
            value()
            if (index + 1 < buttons.size) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}


@Composable
fun MealPlannerButton(
    isActive: Boolean = true,
    text: String,
    isDisabled: Boolean = false,
    modifier: Modifier = Modifier,
    callback: () -> Unit
) {
    val dateModifier =
        if (isDisabled)
            Modifier.border(
                2.dp,
                color = Color(0XFFc8c8c8),
                shape = RoundedCornerShape(7.dp)
            )
        else if (isActive)
            Modifier.background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(7.dp)
            )
        else
            Modifier.border(
                2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(7.dp)
            )

    Box(
        modifier = Modifier
            .then(dateModifier)
            .clickable { callback() }
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .then(modifier)

    ) {
        Text(text)
    }
}