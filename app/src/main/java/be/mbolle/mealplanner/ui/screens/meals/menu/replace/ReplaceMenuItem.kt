package be.mbolle.mealplanner.ui.screens.meals.menu.replace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.mbolle.mealplanner.R
import be.mbolle.mealplanner.model.Menu
import be.mbolle.mealplanner.ui.composables.MealPlannerButton
import be.mbolle.mealplanner.ui.composables.menu.MenuList
import be.mbolle.mealplanner.ui.composables.menu.MenuListItem

@Composable
fun MenuItemReplace(
    modifier: Modifier = Modifier,
    initMenu: Menu,
    menuList: Collection<List<Menu>>
) {
    Column(modifier = modifier) {
        //replace composable
        ReplaceChooser(initMenu, initMenu)

        Spacer(modifier = Modifier.padding(vertical = 30.dp))
        //menu composable should be integrated

        Column(modifier = Modifier.fillMaxHeight(0.9f)) {
            MenuList(
                mealsState = menuList, scrollIndex = 0,
                modifier = Modifier.weight(8f)
            ) { }




        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MealPlannerButton(text = "Cancel") { }
            Spacer(modifier = Modifier.padding(7.dp))
            MealPlannerButton(text = "Confirm") { }
        }
    }
}


@Composable
fun ReplaceChooser(
    initMenu: Menu,
    chosenMenu: Menu?,
) {
    Column {

        Text(
            "Replace",
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
            fontSize = 25.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Light
        )


        Box {
            Column {
                MenuListItem(initMenu, onMealAction = {}, Modifier.padding(10.dp))
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                if (chosenMenu == null) {

                } else {
                    MenuListItem(initMenu, onMealAction = {}, modifier = Modifier.padding(10.dp))
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