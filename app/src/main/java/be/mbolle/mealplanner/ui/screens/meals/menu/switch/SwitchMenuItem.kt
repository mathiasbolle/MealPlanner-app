package be.mbolle.mealplanner.ui.screens.meals.menu.switch

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.mealplanner.R
import be.mbolle.mealplanner.model.Menu
import be.mbolle.mealplanner.ui.composables.MealPlannerButton
import be.mbolle.mealplanner.ui.composables.menu.MenuList
import be.mbolle.mealplanner.ui.composables.menu.MenuListItem

@Composable
fun MenuItemSwitch(
    modifier: Modifier = Modifier,
    menuList: Collection<List<Menu>>,
    navigateBack: () -> Unit
) {
    val switchMenuViewModel: SwitchMenuViewModel = viewModel(factory = SwitchMenuViewModel.Factory)
    val state = switchMenuViewModel.switchMenuState.value


    Column(modifier = modifier) {
        //replace composable
        if (state != null) {
            val state = switchMenuViewModel.switchMenuState.value

            SwitchMenuChooser(state?.fromMenu!!, state.toMenu)

            Spacer(modifier = Modifier.padding(vertical = 30.dp))
            //menu composable should be integrated

            Column(modifier = Modifier.fillMaxHeight(0.9f)) {
                MenuList(
                    mealsState = menuList, scrollIndex = 0,
                    modifier = Modifier.weight(8f),
                    onMenuAction = { meal ->
                        Log.d("ReplaceMenuItem", meal.toString())
                        switchMenuViewModel.replaceMenu(meal)
                    }
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                MealPlannerButton(text = "Cancel") {
                    navigateBack()
                }
                Spacer(modifier = Modifier.padding(7.dp))
                MealPlannerButton(text = "Confirm") {
                    switchMenuViewModel.confirm()
                    navigateBack()
                }
            }
        }
    }
}

@Composable
fun SwitchMenuChooser(
    initMenu: Menu,
    chosenMenu: Menu?,
) {
    Column {
        Text(
            stringResource(R.string.switch_txt),
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
            fontSize = 25.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Light
        )


        Box {
            Column {
                MenuListItem(
                    menu = initMenu,
                    modifier = Modifier.padding(10.dp),
                    highlightToday = false
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                if (chosenMenu == null) {
                    MenuListItem(
                        menu = null,
                        onMealAction = {},
                        modifier = Modifier.padding(10.dp),
                        highlightToday = false
                    )

                } else {
                    MenuListItem(
                        menu = chosenMenu,
                        modifier = Modifier.padding(10.dp),
                        highlightToday = false
                    )
                }
            }
            Icon(
                painterResource(R.drawable.swap),
                "Edit",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = 20.dp)
            )
        }
    }
}