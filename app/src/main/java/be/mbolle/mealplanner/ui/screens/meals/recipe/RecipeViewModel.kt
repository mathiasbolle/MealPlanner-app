package be.mbolle.mealplanner.ui.screens.meals.recipe

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import be.mbolle.mealplanner.data.ApiMealRepository
import be.mbolle.mealplanner.data.MealRepository
import be.mbolle.mealplanner.data.api.Meal
import be.mbolle.mealplanner.data.api.ktorHttpClient
import be.mbolle.mealplanner.data.toMealModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RecipeViewModel(private val mealRepository: MealRepository) : ViewModel() {
    var recipeState: RecipeStatus by mutableStateOf(RecipeStatus.Loading)

    init {
        // load the API
        getRecipe()
    }

    private fun getInitRecipes(): Deferred<RecipeStatus> {
        return viewModelScope.async {
            try {
                val result = mealRepository.getMeal().toMealModel()
                return@async RecipeStatus.Succeed(list = result)
            } catch (e: Exception) {
                return@async RecipeStatus.Error(e.toString())
            }
        }
    }

    fun getRecipe() {
        viewModelScope.launch {
            // should we not use some sort of copy to ensure the immutability
            recipeState = getInitRecipes().await()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val mealService = Meal(client = ktorHttpClient)
                val mealRepository: MealRepository = ApiMealRepository(mealService = mealService)

                return RecipeViewModel(
                    mealRepository = mealRepository
                ) as T
            }
        }
    }
}