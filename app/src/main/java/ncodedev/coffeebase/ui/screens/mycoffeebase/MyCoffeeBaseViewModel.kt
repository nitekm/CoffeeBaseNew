package ncodedev.coffeebase.ui.screens.mycoffeebase

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.model.Coffee
import okio.IOException
import javax.inject.Inject


sealed interface MyCoffeeBaseUiState{
    data class Success(val coffees: List<Coffee>) : MyCoffeeBaseUiState
    object Error: MyCoffeeBaseUiState
    object Loading: MyCoffeeBaseUiState
}

@HiltViewModel
class MyCoffeeBaseViewModel @Inject constructor(
    private val coffeeRepository: CoffeeRepository
) : ViewModel() {

    private val TAG: String = "MyCoffeeBaseModelView"

    var myCoffeeBaseUiState: MyCoffeeBaseUiState by mutableStateOf(MyCoffeeBaseUiState.Loading)
        private set

    init {
        getCoffeesPaged()
    }

    private fun getCoffeesPaged() {
        viewModelScope.launch {
            myCoffeeBaseUiState = MyCoffeeBaseUiState.Loading
            myCoffeeBaseUiState = try {
                MyCoffeeBaseUiState.Success(coffeeRepository.getCoffeesPaged())
            } catch (e: IOException) {
                Log.e(TAG, "GetCoffeesPaged resulted in exception $e")
                MyCoffeeBaseUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "GetCoffeesPaged resulted in exception $e")
                MyCoffeeBaseUiState.Error
            }
        }
    }
}