package ncodedev.coffeebase.ui.screens.mycoffeebase

import kotlinx.coroutines.test.runTest
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.model.Page
import ncodedev.coffeebase.model.PageCoffeeRequest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MyCoffeeBaseViewModelTest {
    private lateinit var viewModel: MyCoffeeBaseViewModel
    private lateinit var repository: CoffeeRepository

    @Before
    fun setup() {
        repository = mock(CoffeeRepository::class.java)
        viewModel = MyCoffeeBaseViewModel(repository)
    }

    @Test
    fun when_getCoffeesPagedIsInvoked_thenMyCoffeeBaseUiStateIsSetToLoading() {
        runTest {
            val request = PageCoffeeRequest()
            `when`(repository.getCoffeesPaged(request)).thenReturn(Page(emptyList(), false, 1))
            // This mock will return an empty list for simplicity. Replace with appropriate return.

            // Act
            viewModel.getCoffeesPaged(request)

            // Assert
            Assert.assertEquals(MyCoffeeBaseUiState.Loading, viewModel.myCoffeeBaseUiState)
        }
    }
}