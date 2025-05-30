package be.mbolle.mealplanner.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.mbolle.mealplanner.model.Meal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMealPlannerModal(
    modifier: Modifier = Modifier,
    meal: Meal,
    onDismissRequest: () -> Unit,
    options: List<Option>,
    isVisible: Boolean,
) {
    MealPlannerModal(
        modifier = modifier,
        title = meal.name,
        innerPaddingValues = PaddingValues(20.dp),
        options = options,
        onDismissRequest = onDismissRequest,
        isVisible = isVisible
    )
}