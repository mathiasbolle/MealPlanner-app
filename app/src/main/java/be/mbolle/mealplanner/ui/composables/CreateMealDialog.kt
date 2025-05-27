package be.mbolle.mealplanner.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import be.mbolle.mealplanner.R


@Composable
fun CreateMealDialog(
    modifier: Modifier = Modifier,
    openDialog: Boolean = false,
    setOpenDialog: (value: Boolean) -> Unit,
    name: String,
    setName: (name: String) -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    MealPlannerDialog(
        onConfirm = onConfirm,
        onCancel = onCancel,
        openDialog = openDialog,
        modifier = modifier,
        title = "Meal Creation",
        setOpenDialog = setOpenDialog,
        questions = listOf(
            Question(name, "Name") { input ->
                setName(input)
            }
        ),
        icon = R.drawable.fridge
    )
}

@Preview
@Composable
fun CreateMealDialogPreview() {
}