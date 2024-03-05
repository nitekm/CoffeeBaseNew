package ncodedev.coffeebase.ui.screens.editcoffee

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.model.Coffee
import javax.inject.Inject

sealed interface EditCoffeeUiState {
    data class Success(val coffee: Coffee): EditCoffeeUiState
    object Error: EditCoffeeUiState
    object Loading: EditCoffeeUiState
}

@HiltViewModel
class EditCoffeeViewModel @Inject constructor(
    private val coffeeRepository: CoffeeRepository
): ViewModel() {

    var myCoffeeBaseUiState by mutableStateOf(EditCoffeeUiState.Loading)
        private set


}