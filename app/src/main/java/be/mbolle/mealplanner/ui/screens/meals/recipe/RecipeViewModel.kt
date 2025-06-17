package be.mbolle.mealplanner.ui.screens.meals.recipe

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import be.mbolle.mealplanner.MainApplication
import be.mbolle.mealplanner.model.Meal
import be.mbolle.mealplanner.model.MealKinds
import be.mbolle.mealplanner.model.MealRepository
import be.mbolle.mealplanner.model.use_cases.GetMealsUseCase
import be.mbolle.mealplanner.ui.screens.meals.menu.MenuStatus
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeViewModel(
    private val mealRepository: MealRepository,
    private val mealsUseCase: GetMealsUseCase
) : ViewModel() {
    var recipeState: RecipeState by mutableStateOf(RecipeState())
        private set

    init {
        // load the API
        getRecipe()
    }

    private fun getInitRecipes(): Deferred<RecipeStatus> {
        return viewModelScope.async {
            try {
                val result = mealsUseCase()
                Log.d("RecipeViewModel", result.toString())
                return@async RecipeStatus.Succeed(list = result)
            } catch (e: Exception) {
                Log.d("RecipeViewModel", e.toString())
                return@async RecipeStatus.Error(e.toString())
            }
        }
    }


    fun choseMeal(meal: Meal) {
        if (recipeState.recipeStatus is RecipeStatus.Succeed) {
            (recipeState.recipeStatus as RecipeStatus.Succeed).let {
                recipeState = recipeState.copy(
                    recipeStatus = it.copy(
                        selectedMeal = meal
                    )
                )
            }
        }
    }

    fun openDialog() {
        val newMeal = recipeState.mealCreation.copy(
            openDialog = true
        )
        recipeState = recipeState.copy(
            mealCreation = newMeal
        )
    }


    fun closeDialog() {
        val newMeal = recipeState.mealCreation.copy(
            openDialog = false
        )
        recipeState = recipeState.copy(
            mealCreation = newMeal
        )
    }

    private fun resetValue() {
        val newMeal = recipeState.mealCreation.copy(
            name = "",
        )
        recipeState = recipeState.copy(
            mealCreation = newMeal
        )
    }

    fun getRecipe() {
        viewModelScope.launch {
            // should we not use some sort of copy to ensure the immutability
            recipeState = recipeState.copy(recipeStatus = getInitRecipes().await())
        }
    }

    fun setName(name: String) {
        val newMeal = recipeState.mealCreation.copy(
            name = name
        )
        recipeState = recipeState.copy(
            mealCreation = newMeal
        )
    }


    fun createRecipe() {
        viewModelScope.launch {
            mealRepository.createIngredientFromMeal(
                be.mbolle.mealplanner.model.Meal(recipeState.mealCreation.name, MealKinds.OTHER)
            )

            withContext(Dispatchers.Main) {
                closeDialog()
                resetValue()
                getRecipe()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return RecipeViewModel(
                    mealsUseCase = GetMealsUseCase(MainApplication.container.remoteMealRepository),
                    mealRepository = MainApplication.container.remoteMealRepository
                ) as T
            }
        }
    }
}