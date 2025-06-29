package be.mbolle.mealplanner.ui.screens.meals.menu.replace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import be.mbolle.mealplanner.ui.screens.meals.menu.replace.subscreen.ReplaceMenuSubScreen

@Composable
fun MenuItemReplace(
    modifier: Modifier = Modifier,
    navigateReplaceMenuCategory: (replaceMenuCategory: ReplaceMenuCategory) -> Unit,
    navigateBack: () -> Unit,
) {
    val viewmodel = viewModel<ReplaceMenuViewModel>(factory = ReplaceMenuViewModel.Factory)
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
                        viewmodel.changeReplaceCategory(category)
                    }
                }
            })


        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            DateButtonsLazyRow(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                buttons = category
            )


            when (state.content) {
                is ReplaceMenuResult.Error -> {

                }

                ReplaceMenuResult.Loading -> {

                }

                is ReplaceMenuResult.Succeed -> {
                    ReplaceMenuSubScreen(
                        replaceMenuFormat = state.content.replaceMenuFormat,
                        replaceMenuCategory = state.selectedCategory,
                        navigateReplaceMenuCategory = { navigateReplaceMenuCategory }
                    )
                }
            }


            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp)
            ) {
                MealPlannerButton(text = "Cancel", isActive = false) {
                    navigateBack()
                }
                Spacer(modifier = Modifier.padding(7.dp))
                MealPlannerButton(text = "Confirm") {
                    navigateBack()
                }
            }
        }

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