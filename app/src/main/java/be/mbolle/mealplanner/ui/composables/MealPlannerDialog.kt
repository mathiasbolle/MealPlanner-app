package be.mbolle.mealplanner.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun MealPlannerDialog(
    modifier: Modifier = Modifier,
    openDialog: Boolean = false,
    title: String,
    setOpenDialog: (open: Boolean) -> Unit,
    icon: Int,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,

    questions: List<Question>
) {
    if (openDialog) {
        Dialog(onDismissRequest = { setOpenDialog(false) }) {
            Card(
                modifier = modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor =
                        Color(0XFFf5eed8),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(
                    modifier
                        .padding(50.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(painterResource(icon), "")
                    Text(
                        title,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )

                    questions.forEach { question ->
                        MealPlannerInput(
                            text = question.content,
                            onValueChange = question.onValueChange,
                            label = question.label
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        MealPlannerButton(text = "Cancel") { onCancel() }
                        Spacer(modifier = Modifier.padding(7.dp))
                        MealPlannerButton(text = "Confirm") { onConfirm() }
                    }
                }
            }
        }
    }
}


@Composable
fun MealPlannerInput(
    text: String,
    onValueChange: (text: String) -> Unit,
    label: String,
) {
    OutlinedTextField(
        modifier = Modifier.padding(vertical = 15.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(
                0XFFe9d08b
            ), focusedLabelColor = Color(0XFFe9d08b)
        ),
        onValueChange = onValueChange,
        value = text,
        label = { Text(label) },
    )
}

data class Question(
    val content: String,
    val label: String,
    val onValueChange: (text: String) -> Unit
)