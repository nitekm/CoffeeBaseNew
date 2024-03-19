package ncodedev.coffeebase.ui.screens.coffeescreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.model.Coffee
import java.io.IOException
import javax.inject.Inject

sealed interface CoffeeUiState {
    data class Success(val coffee: Coffee) : CoffeeUiState
    object Error : CoffeeUiState
    object Loading : CoffeeUiState
}

@HiltViewModel
class CoffeeViewModel @Inject constructor(
    private val coffeeRepository: CoffeeRepository
) : ViewModel() {
    companion object {
        private const val TAG = "CoffeeViewModel"
    }

    val coffee = mutableStateOf<Coffee?>(null)
    var coffeeUiState: CoffeeUiState by mutableStateOf(CoffeeUiState.Loading)
        private set

    fun getCoffee(coffeeId: Long) {
        viewModelScope.launch {
            coffeeUiState = CoffeeUiState.Loading
            coffeeUiState = try {
                Log.i(TAG, "CoffeeViewModel response from API!")
                CoffeeUiState.Success(coffeeRepository.getCoffee(coffeeId))
            } catch (e: IOException) {
                Log.e(TAG, "getCoffee resulted in exception $e")
                CoffeeUiState.Error
            }
        }
    }
}


