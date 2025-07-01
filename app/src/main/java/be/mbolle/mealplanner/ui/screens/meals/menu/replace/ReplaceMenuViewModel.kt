package be.mbolle.mealplanner.ui.screens.meals.menu.replace

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import be.mbolle.mealplanner.MainApplication
import be.mbolle.mealplanner.model.MealKinds
import be.mbolle.mealplanner.model.MealRepository
import be.mbolle.mealplanner.model.Menu
import be.mbolle.mealplanner.model.use_cases.GetIngredientsUseCase
import be.mbolle.mealplanner.model.use_cases.GetMealsUseCase
import be.mbolle.mealplanner.model.use_cases.GetSpecificIngredientUseCase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ReplaceMenuViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val mealRepository: MealRepository,
    private val ingredientsUseCase: GetSpecificIngredientUseCase,
    private val mealsUseCase: GetMealsUseCase
) : ViewModel() {
    var state by mutableStateOf(ReplaceMenuState())
        private set

    init {
        initMenu()
    }

    // same function as SwitchMenuViewModel
    // perhaps we should extract this to a separate file?
    private fun initMenu() {
        viewModelScope.launch {
            val menuId: Int = savedStateHandle["menu"] ?: 0
            if (menuId != 0) {
                Log.d("ReplaceMenuViewModel - menu display", menuId.toString())

                val initMeal = mealRepository.getMenuById(menuId)
                state = ReplaceMenuState(menu = Menu(initMeal.id, date = initMeal.date, meal = ""))

                changeReplaceCategory(state.selectedCategory)
            }
        }
    }

    fun changeReplaceCategory(replaceMenuCategory: ReplaceMenuCategory) {
        viewModelScope.launch {
            state = state.copy(
                selectedCategory = replaceMenuCategory,
                content = getInitIngredients(replaceMenuCategory).await()
            )
        }
    }

    private fun getInitIngredients(replaceMenuCategory: ReplaceMenuCategory): Deferred<ReplaceMenuResult> {
        return viewModelScope.async {
            try {
                when (replaceMenuCategory) {
                    ReplaceMenuCategory.CUSTOM -> {
                        val mealKinds = enumValues<MealKinds>().distinct()

                        val customMeals = mealKinds.map { mealKind ->
                            val key = mealKind.ordinal + 1
                            val data = ingredientsUseCase(mealKind)


                            return@map Pair(key, data)
                        }.toTypedArray()
                        return@async ReplaceMenuResult.Succeed(
                            ReplaceMenuFormat.Custom(
                                customMap = mapOf(*customMeals)
                            )
                        )
                    }

                    ReplaceMenuCategory.PREDEFINED -> {
                        return@async ReplaceMenuResult.Succeed(
                            ReplaceMenuFormat.Predefined(
                                list = mealsUseCase()
                            )
                        )
                    }
                }
            } catch (_: Exception) {
                return@async ReplaceMenuResult.Error("error with the request")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val mealRepository = MainApplication.container.remoteMealRepository
                val ingredientUseCase = GetIngredientsUseCase(mealRepository)
                return ReplaceMenuViewModel(
                    savedStateHandle = extras.createSavedStateHandle(),
                    mealRepository = mealRepository,
                    ingredientsUseCase = GetSpecificIngredientUseCase(ingredientUseCase),
                    mealsUseCase = GetMealsUseCase(mealRepository)
                ) as T
            }
        }
    }
}