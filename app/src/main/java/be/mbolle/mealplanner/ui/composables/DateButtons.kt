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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

        /*
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(7.dp)
                )
                .clickable { onClickCurrentWeek() }
                .padding(horizontal = 10.dp, vertical = 5.dp)

        ) {
            Text(stringResource(R.string.this_week_btn))
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
            Text(stringResource(R.string.next_week_btn))
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
            Text(stringResource(R.string.next_month_btn))
        }
    }
         */

    }
}

@Composable
fun DateButton(
    isActive: Boolean,
    text: String,
    callback: () -> Unit
) {
    val dateModifier = if (isActive)
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

    ) {
        Text(text)
    }
}