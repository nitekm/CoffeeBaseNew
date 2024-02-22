package ncodedev.coffeebase.ui.screens.mycoffeebase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.model.Page
import ncodedev.coffeebase.model.PageCoffeeRequest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class MyCoffeeBaseViewModelTest {
    private lateinit var viewModel: MyCoffeeBaseViewModel
    private lateinit var repository: CoffeeRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(CoffeeRepository::class.java)
        viewModel = MyCoffeeBaseViewModel(repository)
    }

    @Test
    fun `when getCoffeesPaged is called and repository returns value then MyCoffeeBaseUiState is Success`() = runTest {
        val request = PageCoffeeRequest()
        val coffeesPage = Page(emptyList<Coffee>(), false, 1)
        `when`(repository.getCoffeesPaged(request)).thenReturn(coffeesPage)

        launch { viewModel.getCoffeesPaged(request) }

        advanceUntilIdle()

        Assert.assertTrue(viewModel.myCoffeeBaseUiState is MyCoffeeBaseUiState.Success)
    }

    @Test
    //TODO: change name
    fun `when getCffoffeesPaged is called and repository returns value then MyCoffeeBaseUiState is Success`() = runTest {
        val request = PageCoffeeRequest()
        val coffeesPage = Page(emptyList<Coffee>(), false, 1)
        `when`(repository.getCoffeesPaged(request)).thenReturn(coffeesPage)

        launch { viewModel.getCoffeesPaged(request) }

//        advanceUntilIdle()

        Assert.assertTrue(viewModel.myCoffeeBaseUiState is MyCoffeeBaseUiState.Loading)
    }
}