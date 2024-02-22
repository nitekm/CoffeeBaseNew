package ncodedev.coffeebase.ui.screens.mycoffeebase

import android.util.Log
import androidx.annotation.VisibleForTesting
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
import ncodedev.coffeebase.model.Page
import ncodedev.coffeebase.model.PageCoffeeRequest
import okio.IOException
import javax.inject.Inject


sealed interface MyCoffeeBaseUiState{
    data class Success(val coffeesPage: Page<Coffee>) : MyCoffeeBaseUiState
    object Error: MyCoffeeBaseUiState
    object Loading: MyCoffeeBaseUiState
}

@HiltViewModel
class MyCoffeeBaseViewModel @Inject constructor(
    private val coffeeRepository: CoffeeRepository
) : ViewModel() {

    private var request = PageCoffeeRequest()

    private val TAG: String = "MyCoffeeBaseModelView"

    var myCoffeeBaseUiState: MyCoffeeBaseUiState by mutableStateOf(MyCoffeeBaseUiState.Loading)
        private set

    fun fetchInitData() {
        getCoffeesPaged(request)
    }

    @VisibleForTesting
    fun getCoffeesPaged(request: PageCoffeeRequest) {
        viewModelScope.launch {
            myCoffeeBaseUiState = MyCoffeeBaseUiState.Loading
            myCoffeeBaseUiState = try {
                MyCoffeeBaseUiState.Success(coffeeRepository.getCoffeesPaged(request))
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