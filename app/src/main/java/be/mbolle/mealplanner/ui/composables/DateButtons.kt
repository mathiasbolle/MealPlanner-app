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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import be.mbolle.mealplanner.R

@Composable
fun DateButtons(
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
}
