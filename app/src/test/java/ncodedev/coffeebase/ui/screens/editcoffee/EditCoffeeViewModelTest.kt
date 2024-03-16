package ncodedev.coffeebase.ui.screens.editcoffee

import android.content.Context
import android.graphics.Bitmap
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import ncodedev.coffeebase.data.repository.CoffeeRepository
import org.junit.Before
import org.junit.Test

class EditCoffeeViewModelTest {

    private lateinit var viewModel: EditCoffeeViewModel

    @MockK
    private lateinit var repository: CoffeeRepository

    @MockK
    private lateinit var context: Context

    @MockK
    private lateinit var coffeeImage: Bitmap

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = EditCoffeeViewModel(repository)
    }

    //test all validations,
    //check if saveCoffee is called with proper coffe object - check only name and all data
    // check image - if null is passed when there is no image, and if image is passed when image is present

    @Test
    fun saveCoffee_doesNotCallRepository_whenCoffeeNameIsEmpty() = runTest {
        viewModel.coffeeName.value = ""
        viewModel.saveCoffee(context, null)
        coVerify(exactly = 0) { repository.saveCoffee(any(), any()) }
    }


}