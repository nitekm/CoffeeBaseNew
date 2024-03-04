package ncodedev.coffeebase.ui.screens.mycoffeebase

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.model.PageCoffeeRequest
import ncodedev.coffeebase.model.SortOptions
import okio.IOException
import javax.inject.Inject


sealed interface MyCoffeeBaseUiState {
    data class Success(val coffees: List<Coffee>) : MyCoffeeBaseUiState
    object Error : MyCoffeeBaseUiState
    object Loading : MyCoffeeBaseUiState
}

@HiltViewModel
class MyCoffeeBaseViewModel @Inject constructor(
    private val coffeeRepository: CoffeeRepository
) : ViewModel() {

    private var coffees by mutableStateOf<List<Coffee>>(emptyList())
    val currentFilters: MutableLiveData<Map<String, Set<String>>> = MutableLiveData(emptyMap())
    private var lastRequest by mutableStateOf(PageCoffeeRequest())
    private var isLastPage by mutableStateOf(false)

    private val TAG: String = "MyCoffeeBaseModelView"

    var myCoffeeBaseUiState: MyCoffeeBaseUiState by mutableStateOf(MyCoffeeBaseUiState.Loading)
        private set

    fun fetchInitData() {
        val request = PageCoffeeRequest()
        getCoffeesPaged(request)
    }

    fun fetchSorted(sortOptions: SortOptions) {
        val request = lastRequest.copy(
            sortProperty = sortOptions.sortProperty,
            sortDirection = sortOptions.sortDirection
        )
        getCoffeesPaged(request)
    }

    fun fetchFiltered(newFilters: Map<String, Set<String>>) {
        val request = lastRequest.copy(filters = newFilters)
        getCoffeesPaged(request)
    }

    fun searchCoffees(searchQuery: String) {
        viewModelScope.launch {
            myCoffeeBaseUiState = MyCoffeeBaseUiState.Loading
            myCoffeeBaseUiState = try {
                MyCoffeeBaseUiState.Success(coffeeRepository.searchCoffees(searchQuery))
            } catch (e: IOException) {
                Log.e(TAG, "GetCoffeesPaged resulted in exception $e")
                MyCoffeeBaseUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "GetCoffeesPaged resulted in exception $e")
                MyCoffeeBaseUiState.Error
            }
        }
    }
    @VisibleForTesting
    fun getCoffeesPaged(request: PageCoffeeRequest, fetchingMore: Boolean = false) {
        viewModelScope.launch {
            myCoffeeBaseUiState = MyCoffeeBaseUiState.Loading
            myCoffeeBaseUiState = try {
                val response = coffeeRepository.getCoffeesPaged(request)
                val allCoffees = if (fetchingMore) {
                    coffees + response.content
                } else {
                    response.content
                }
                isLastPage = response.isLastPage
                lastRequest = request
                currentFilters.value = request.filters
                MyCoffeeBaseUiState.Success(allCoffees)
            } catch (e: IOException) {
                Log.e(TAG, "GetCoffeesPaged resulted in exception $e")
                MyCoffeeBaseUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "GetCoffeesPaged resulted in exception $e")
                MyCoffeeBaseUiState.Error
            }
        }
    }

    fun fetchMore() {
        if (isLastPage) {
            return
        }
        val request: PageCoffeeRequest = lastRequest.copy(pageNumber = lastRequest.pageNumber + 1)
        lastRequest = request
        getCoffeesPaged(request)
    }
}
