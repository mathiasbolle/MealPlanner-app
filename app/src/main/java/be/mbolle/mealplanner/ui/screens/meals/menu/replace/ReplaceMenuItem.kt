package be.mbolle.mealplanner.ui.screens.meals.menu.replace

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.mealplanner.model.Menu
import be.mbolle.mealplanner.ui.composables.DateButtonsLazyRow
import be.mbolle.mealplanner.ui.composables.MealPlannerButton
import be.mbolle.mealplanner.ui.composables.menu.MenuListItem
import java.util.Locale

@Composable
fun MenuItemReplace(
    modifier: Modifier = Modifier,

    navigateBack: () -> Unit,
) {
    val viewmodel: ReplaceMenuViewModel = viewModel()
    val state = viewmodel.state

    Column(modifier = modifier) {
        ReplaceMenuHeader(
            state.selectedCategory,
            state.menu
        )
        val category: List<@Composable () -> Unit> =
            (enumValues<ReplaceMenuCategory>().map { category ->
                {
                    MealPlannerButton(
                        category == state.selectedCategory,
                        category.toString()
                    ) {
                    }
                }
            })


        DateButtonsLazyRow(
            modifier = Modifier.fillMaxWidth(),
            buttons = category
        )

    }
}


@Composable
fun ReplaceMenuHeader(
    replaceMenuCategory: ReplaceMenuCategory,
    menu: Menu?
) {
    Column {
        Text(
            replaceMenuCategory.toString(),
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
            fontSize = 25.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Light
        )

        MenuListItem(
            menu = menu,
            modifier = Modifier.padding(10.dp),
            highlightToday = false
        )
    }
}

enum class ReplaceMenuCategory(private var category: String) {
    CUSTOM("Custom"), PREDEFINED("Predefined");

    override fun toString(): String {
        return category
    }
}