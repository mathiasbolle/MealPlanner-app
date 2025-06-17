package be.mbolle.mealplanner.ui.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import be.mbolle.mealplanner.R
import be.mbolle.mealplanner.model.isToday


@Composable
fun MealPlannerList(
    modifier: Modifier = Modifier,
    isToday: Boolean = false,
    paddingValues: PaddingValues = PaddingValues(),
    onClickOptions: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val backgroundColor =
        if (isToday) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer

    // should be derived from a viewmodel, this is presentation logic
    if (isToday) FontWeight.Bold
    else FontWeight.Normal
    Row(
        modifier = Modifier
            .padding(paddingValues)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10)
            )
            .height(IntrinsicSize.Min)
            .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .weight(0.85f)
                .fillMaxHeight()
        ) {
            content()
        }

        Column(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (onClickOptions != null)  {
                Icon(
                    modifier = Modifier.clickable {
                        onClickOptions()
                        Log.d("MealPlannerList", "this is a test..;")
                    },
                    painter = painterResource(R.drawable.more_horiz),
                    contentDescription = stringResource(
                        R.string.more_icon
                    )
                )

            }

        }
    }
}