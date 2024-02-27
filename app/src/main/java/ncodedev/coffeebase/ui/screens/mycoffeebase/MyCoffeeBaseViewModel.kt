package ncodedev.coffeebase.ui.screens.mycoffeebase

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.model.FilterOptions
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
    private var lastRequest by mutableStateOf(PageCoffeeRequest())
    private var isLastPage by mutableStateOf(false)

    private val TAG: String = "MyCoffeeBaseModelView"

    var myCoffeeBaseUiState: MyCoffeeBaseUiState by mutableStateOf(MyCoffeeBaseUiState.Loading)
        private set

    fun fetchInitData() {
        val request = PageCoffeeRequest()
        lastRequest = request
        getCoffeesPaged(request)
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

    fun fetchSorted(sortOptions: SortOptions) {
        val request = PageCoffeeRequest(
            sortProperty = sortOptions.sortProperty,
            sortDirection = sortOptions.sortDirection
        )
        lastRequest = request
        getCoffeesPaged(request)
    }

    fun fetchFiltered(filters: SnapshotStateMap<FilterOptions, Boolean>) {
        val request = lastRequest.copy(filters = filters)
        lastRequest = request
        getCoffeesPaged(request)
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