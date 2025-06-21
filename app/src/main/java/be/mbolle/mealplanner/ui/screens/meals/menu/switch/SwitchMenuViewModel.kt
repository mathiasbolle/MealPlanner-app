package be.mbolle.mealplanner.ui.screens.meals.menu.switch

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import be.mbolle.mealplanner.MainApplication
import be.mbolle.mealplanner.model.MealRepository
import be.mbolle.mealplanner.model.Menu
import kotlinx.coroutines.launch

class SwitchMenuViewModel(
    //receive this with parameter of
    private val savedStateHandle: SavedStateHandle,
    private val mealRepository: MealRepository,
) : ViewModel() {

    var switchMenuState: MutableState<SwitchMenuState?> =
        mutableStateOf(
            null
        )
        private set

    init {
        // init
        initMenu()
    }

    private fun initMenu() {
        viewModelScope.launch {
            val menuId: Int = savedStateHandle["menu"] ?: 0
            Log.d("ReplaceMenuViewModel", menuId.toString())

            val initMeal = mealRepository.getMenuById(menuId)
            switchMenuState.value = SwitchMenuState(fromMenu = initMeal, toMenu = null)
        }
        Log.d("ReplaceMenuViewModel", switchMenuState.value.toString())
    }

    fun replaceMenu(menu: Menu) {
        switchMenuState.value = switchMenuState.value?.copy(
            toMenu = menu
        )
    }


    fun confirm() {
        viewModelScope.launch {
            if (switchMenuState.value != null) {
                val id: Int = switchMenuState.value?.toMenu?.id ?: 0
                val menu: Menu = switchMenuState.value?.fromMenu!!
                mealRepository.switchMenu(
                    id,
                    menu
                )
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return SwitchMenuViewModel(
                    savedStateHandle = extras.createSavedStateHandle(),
                    mealRepository = MainApplication.container.remoteMealRepository
                ) as T
            }
        }
    }
}