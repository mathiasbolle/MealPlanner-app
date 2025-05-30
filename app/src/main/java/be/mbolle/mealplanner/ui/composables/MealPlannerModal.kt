package be.mbolle.mealplanner.ui.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.HistoricalChange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.mbolle.mealplanner.ui.theme.MealPlannerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlannerModal(
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues,
    title: String,
    options: List<Option>,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismissRequest: () -> Unit,
    isVisible: Boolean,
) {
    Log.d("MealPlannerModal", isVisible.toString())
    if (isVisible) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = Color.Black
        ) {
            Column(modifier = Modifier.padding(innerPaddingValues)) {
                val defaultPadding = 20.dp
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = defaultPadding)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black)
                )

                options.forEachIndexed { index, option ->
                    if (index == 0) {
                        Text(
                            option.content,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .clickable { option.onPressOption() }
                                .padding(
                                    top = defaultPadding,
                                    bottom = defaultPadding / 4
                                )
                        )
                    } else if (index == options.size - 1) {
                        Text(
                            option.content,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .clickable { option.onPressOption() }
                                .padding(bottom = defaultPadding)
                        )
                    } else {
                        Text(
                            option.content,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .clickable { option.onPressOption() }
                                .padding(bottom = defaultPadding / 4)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewMealPlannerModal() {

    MealPlannerTheme {
        val sheetState =
            rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded)
        MealPlannerModal(
            sheetState = sheetState,
            title = "Vleeskoek met boontjes",
            options = listOf(
                Option("This", {}),
                Option("Is", {}),
                Option("An", {}),
                Option("Example", {}),
            ),
            innerPaddingValues = PaddingValues(20.dp),
            isVisible = true,
            onDismissRequest = {}
        )
    }
}

data class Option(val content: String, val onPressOption: () -> Unit)