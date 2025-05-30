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
import be.mbolle.mealplanner.R


@Composable
fun MealPlannerList(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onClickOptions: () -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(paddingValues)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
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