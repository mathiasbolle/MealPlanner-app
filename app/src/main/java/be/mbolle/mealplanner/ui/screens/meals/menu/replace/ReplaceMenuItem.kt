package be.mbolle.mealplanner.ui.screens.meals.menu.replace

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.mealplanner.model.Menu
import be.mbolle.mealplanner.ui.composables.MealPlannerListLazy
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
        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            MealPlannerListLazy<ReplaceMenuCategory>(
                modifier = Modifier
                    .fillMaxWidth(),
                values = enumValues<ReplaceMenuCategory>(),
                isActiveValue = state.selectedCategory
            ) { category ->
                viewmodel.changeReplaceCategory(category)
            }
            when (state.content) {
                is ReplaceMenuResult.Error -> {

                }

                ReplaceMenuResult.Loading -> {

                }

                is ReplaceMenuResult.Succeed -> {
                    var currentPageState by remember { mutableIntStateOf(-1) }
                    Log.d("ReplaceMenuItem", currentPageState.toString())


                    ReplaceMenuSubScreen(
                        unselectMealCategory = { meal -> viewmodel.unselectMealCategory(meal) },
                        selectMealCategory = { meal -> viewmodel.selectMeatCategory(meal) },
                        navigateBack = navigateBack,
                        replaceMenuFormat = state.content.replaceMenuFormat,
                        replaceMenuCategory = state.selectedCategory,
                        navigateReplaceMenuCategory = { navigateReplaceMenuCategory }
                    )
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