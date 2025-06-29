package be.mbolle.mealplanner.ui.screens.meals.menu.replace.subscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import be.mbolle.mealplanner.ui.composables.menu.MealList
import be.mbolle.mealplanner.ui.screens.meals.menu.replace.ReplaceMenuCategory
import be.mbolle.mealplanner.ui.screens.meals.menu.replace.ReplaceMenuFormat

@Composable
fun ReplaceMenuSubScreen(
    modifier: Modifier = Modifier,
    replaceMenuFormat: ReplaceMenuFormat,
    replaceMenuCategory: ReplaceMenuCategory,
    navigateReplaceMenuCategory: (replaceMenuCategory: ReplaceMenuCategory) -> Unit
) {
    LaunchedEffect(replaceMenuCategory) {
        navigateReplaceMenuCategory(replaceMenuCategory) // probably bad practise
    }

    when (replaceMenuCategory) {
        ReplaceMenuCategory.CUSTOM -> {
            val customMenuFormat = (replaceMenuFormat as ReplaceMenuFormat.Custom)
            val pagerState =
                rememberPagerState(pageCount = { customMenuFormat.customMap.size }) // hard coded for now
            HorizontalPager(
                state = pagerState,
            ) { page ->
                MealList(mealList = customMenuFormat.customMap[page + 1] ?: emptyList()) { }


            }

            Log.d("ReplaceMenuSubScreen - test", pagerState.pageCount.toString())
            PagerIndicator(
                modifier = Modifier.padding(vertical = 10.dp),
                pageCount = pagerState.pageCount,
                currentPage = pagerState.currentPage
            )
        }

        ReplaceMenuCategory.PREDEFINED -> {
            val predefinedFormat = (replaceMenuFormat as ReplaceMenuFormat.Predefined)
            MealList(mealList = predefinedFormat.list) { }
        }
    }
}

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        repeat(pageCount) { iteration ->
            val color = if (currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }


}