package ncodedev.coffeebase.ui.screens.mycoffeebase

import coil.network.HttpException
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
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.io.IOException

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
    fun `when getCoffeesPaged is called then MyCoffeeBaseUiState is Loading`() = runTest {
        val request = PageCoffeeRequest()
        val coffeesPage = Page(emptyList<Coffee>(), false, 1)
        `when`(repository.getCoffeesPaged(request)).thenReturn(coffeesPage)

        launch { viewModel.getCoffeesPaged(request) }

        Assert.assertTrue(viewModel.myCoffeeBaseUiState is MyCoffeeBaseUiState.Loading)
    }

    @Test
    fun `when getCoffeesPaged is called and IOException is threw MyCoffeeBaseUiState is Error`() = runTest {
        val request = PageCoffeeRequest()
        `when`(repository.getCoffeesPaged(request)).thenAnswer{ throw IOException()}

        launch { viewModel.getCoffeesPaged(request) }

        advanceUntilIdle()

        Assert.assertTrue(viewModel.myCoffeeBaseUiState is MyCoffeeBaseUiState.Error)
    }

    @Test
    fun `when getCoffeesPaged is called and HttpException is threw MyCoffeeBaseUiState is Error`() = runTest {
        val request = PageCoffeeRequest()
        val response = Response.Builder()
            .code(404)
            .request(Request.Builder().url("http://test").build())
            .protocol(Protocol.HTTP_2)
            .message("error")
            .build()
        `when`(repository.getCoffeesPaged(request)).thenAnswer{ throw HttpException(response)}

        launch { viewModel.getCoffeesPaged(request) }

        advanceUntilIdle()

        Assert.assertTrue(viewModel.myCoffeeBaseUiState is MyCoffeeBaseUiState.Error)
    }
}