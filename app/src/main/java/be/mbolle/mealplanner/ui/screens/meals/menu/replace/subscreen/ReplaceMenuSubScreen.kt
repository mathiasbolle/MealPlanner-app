package be.mbolle.mealplanner.ui.screens.meals.menu.replace.subscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.mbolle.mealplanner.model.Meal
import be.mbolle.mealplanner.model.MealKinds
import be.mbolle.mealplanner.ui.composables.MealPlannerButton
import be.mbolle.mealplanner.ui.composables.menu.MealList
import be.mbolle.mealplanner.ui.screens.meals.menu.replace.ReplaceMenuCategory
import be.mbolle.mealplanner.ui.screens.meals.menu.replace.ReplaceMenuFormat
import be.mbolle.mealplanner.ui.theme.MealPlannerTheme

@Composable
fun ReplaceMenuSubScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    selectMealCategory: (meal: Meal) -> Unit,
    unselectMealCategory: (meal: Meal) -> Unit,
    replaceMenuFormat: ReplaceMenuFormat,
    replaceMenuCategory: ReplaceMenuCategory,
    navigateReplaceMenuCategory: (replaceMenuCategory: ReplaceMenuCategory) -> Unit,
) {
    var customMealsPager: PagerState? = null
    LaunchedEffect(replaceMenuCategory) {
        navigateReplaceMenuCategory(replaceMenuCategory) // probably bad practise
    }

    when (replaceMenuCategory) {
        ReplaceMenuCategory.CUSTOM -> {
            val customMenuFormat = (replaceMenuFormat as ReplaceMenuFormat.Custom)
            customMealsPager =
                rememberPagerState(pageCount = { customMenuFormat.customMap.size })

            HorizontalPager(
                state = customMealsPager,
                modifier = modifier,
            ) { page ->
                MealList(
                    mealList = customMenuFormat.customMap[page + 1] ?: emptyList(),
                    onClickItem = { meal ->

                        when (meal.mealKind) {
                            MealKinds.PASTA_PATATO -> {
                                if (customMenuFormat.selectedPatatoes == null) {
                                    selectMealCategory(meal)
                                } else {
                                    unselectMealCategory(meal)
                                }

                            }
                            MealKinds.VEGETABLES -> {
                                if (customMenuFormat.selectedVegetables == null) {
                                    selectMealCategory(meal)
                                } else {
                                    unselectMealCategory(meal)
                                }

                            }
                            MealKinds.MEAT -> {
                                if (customMenuFormat.selectedMeat == null) {
                                    selectMealCategory(meal)
                                } else {
                                    unselectMealCategory(meal)
                                }
                            }
                            MealKinds.DISH -> {

                            }
                            MealKinds.OTHER -> {

                            }
                            MealKinds.SAUCE -> {

                            }
                        }


                    }
                )
            }

            Log.d("ReplaceMenuSubScreen - test", customMealsPager.pageCount.toString())
            PagerIndicator(
                modifier = Modifier.padding(vertical = 10.dp),
                pageCount = customMealsPager.pageCount,
                currentPage = customMealsPager.currentPage
            )
        }

        ReplaceMenuCategory.PREDEFINED -> {
            val predefinedFormat = (replaceMenuFormat as ReplaceMenuFormat.Predefined)
            MealList(mealList = predefinedFormat.list)
        }
    }
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 20.dp)
    ) {
        MealPlannerButton(text = "Cancel", isActive = false) {
            navigateBack()
        }
        Spacer(modifier = Modifier.padding(7.dp))

        val coroutineScope = rememberCoroutineScope()
        MealPlannerButton(text = if (customMealsPager == null || customMealsPager.currentPage != 5) "Next" else "Confirm") {
//            if (pagerState.currentPage == -1) {
//                navigateBack()
//            } else if (pagerState.currentPage != 5) {
//                coroutineScope.launch {
//                    pagerState.scrollToPage(pagerState.targetPage + 1)
//                }
//            }
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


@Preview
@Composable
fun ReplaceMenuSubScreenCustomPreview() {
    val customMap = mapOf<Int, List<Meal>>(
        1 to
                listOf(
                    Meal("test", MealKinds.PASTA_PATATO),
                    Meal("test", MealKinds.PASTA_PATATO),
                    Meal("test", MealKinds.PASTA_PATATO),
                    Meal("test", MealKinds.PASTA_PATATO),

                    ),
        2 to

                listOf(
                    Meal("test", MealKinds.PASTA_PATATO),
                    Meal("test", MealKinds.PASTA_PATATO),
                    Meal("test", MealKinds.PASTA_PATATO),
                    Meal("test", MealKinds.PASTA_PATATO),

                    )

    )

    MealPlannerTheme {
        ReplaceMenuSubScreen(
            navigateBack = {},
            navigateReplaceMenuCategory = {},
            replaceMenuFormat = ReplaceMenuFormat.Custom(
                customMap = customMap
            ),
            replaceMenuCategory = ReplaceMenuCategory.CUSTOM,
            selectMealCategory = {},
            unselectMealCategory = {}
        )
    }
}