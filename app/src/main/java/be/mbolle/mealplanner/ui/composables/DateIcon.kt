package be.mbolle.mealplanner.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import be.mbolle.mealplanner.util.getAbbrDay
import java.time.LocalDate

@Composable
fun DateIcon(modifier: Modifier = Modifier, date: LocalDate) {
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
