package be.mbolle.mealplanner.ui.screens.ingredients

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
import be.mbolle.mealplanner.model.use_cases.GetIngredientsUseCase
import be.mbolle.mealplanner.model.use_cases.GetSpecificIngredientUseCase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class IngredientViewModel(
    private val getIngredientsUseCase: GetIngredientsUseCase,
    private val getSpecificIngredientUseCase: GetSpecificIngredientUseCase,
    private val remoteMealRepository: MealRepository,
) : ViewModel() {
    var ingredientState: IngredientState by mutableStateOf(IngredientState())
        private set

    init {
        loadIngredientData() // remote API
        getIngredients() // local cache
    }

    private fun getInitIngredients(): Deferred<IngredientStatus> {
        return viewModelScope.async {
            try {
                val result = getIngredientsUseCase()

                val mealCategories = result.map { meal: Meal -> meal.mealKind }.distinct()

                return@async IngredientStatus.Succeed(
                    list = result,
                    ingredientCategories = mealCategories
                )
            } catch (e: Exception) {
                return@async IngredientStatus.Error(e.message.toString())
            }
        }
    }

    fun selectIngredientCategory(mealKinds: MealKinds) {

        viewModelScope.launch {
            val filteredMeals = getSpecificIngredientUseCase(mealKinds)

            val succeededIngredients = ingredientState.ingredientStatus as IngredientStatus.Succeed

            ingredientState = ingredientState.copy(
                ingredientStatus = succeededIngredients.copy(
                    selectedCategory = mealKinds,
                    list = filteredMeals
                )
            )
        }
    }

    fun unselectIngredientCategory() {
        val succeededIngredients = ingredientState.ingredientStatus as IngredientStatus.Succeed


        viewModelScope.launch {
            val meals = getIngredientsUseCase()

            ingredientState = ingredientState.copy(
                ingredientStatus = succeededIngredients.copy(
                    selectedCategory = null,
                    list = meals
                )
            )
        }

    }

    private fun getIngredients() {
        viewModelScope.launch {
            Log.d("IngredientViewModel", getInitIngredients().await().toString())
            ingredientState = ingredientState.copy(ingredientStatus = getInitIngredients().await())
        }
    }

    private fun loadIngredientData() {
        viewModelScope.launch {
            remoteMealRepository.refreshFoodItems()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val getIngredientsUseCase =
                    GetIngredientsUseCase(MainApplication.container.remoteMealRepository)
                return IngredientViewModel(
                    getIngredientsUseCase = getIngredientsUseCase,
                    getSpecificIngredientUseCase = GetSpecificIngredientUseCase(getIngredientsUseCase),
                    remoteMealRepository = MainApplication.container.remoteMealRepository
                ) as T
            }
        }
    }
}