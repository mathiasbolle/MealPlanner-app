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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import be.mbolle.mealplanner.R
import be.mbolle.mealplanner.model.isToday

@Composable
fun MealPlannerList(
    modifier: Modifier = Modifier,
    isToday: Boolean = false,
    hasSelectedMeal: Boolean = false,
    paddingValues: PaddingValues = PaddingValues(),
    onClickOptions: (() -> Unit)? = null,
    onClickItem: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }

    var backgroundColor  =
            if (isClicked) Color.Yellow
            else if (isToday) MaterialTheme.colorScheme.tertiaryContainer
            else if (hasSelectedMeal) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.primaryContainer

    // should be derived from a viewmodel, this is presentation logic
    if (isToday) FontWeight.Bold
    else FontWeight.Normal

    val additionalModifier = if (onClickItem != null) Modifier.clickable {
        Log.d("MealPlannerList", "clicked!!")
        onClickItem()
        isClicked = !isClicked
    } else Modifier

    var baseRowModifier: Modifier = Modifier
        .padding(paddingValues)
        .clip(
            shape = RoundedCornerShape(10)
        )

        .background(
            color = backgroundColor,
        )
        .then(additionalModifier)
        .height(IntrinsicSize.Min)
        .then(modifier)




    Row(
        modifier = baseRowModifier
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
            if (onClickOptions != null) {
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