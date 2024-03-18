package ncodedev.coffeebase.ui.screens.editcoffee.tabs

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.ui.screens.editcoffee.EditCoffeeViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OtherCoffeeInfoTabTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: EditCoffeeViewModel

    @MockK
    private lateinit var repository: CoffeeRepository


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = EditCoffeeViewModel(repository)
        composeTestRule.setContent {
            OtherCoffeeInfo(editCoffeeViewModel = viewModel)
        }
    }
    //processing sca crop height
    @Test
    fun otherCoffeeInfoTab_processingInputText_changesViewModelProcessingField() {
        composeTestRule.onNodeWithTag("Processing").assertIsDisplayed()
        val processing = "test processing"
        composeTestRule.onNodeWithTag("Processing").performTextInput(processing)
        assert(viewModel.processing.value == processing)
    }

    @Test
    fun otherCoffeeInfoTab_scaRatingInputText_changesViewModelScaRatingField() {
        composeTestRule.onNodeWithTag("SCA Score").assertIsDisplayed()
        val scaRating = "90"
        composeTestRule.onNodeWithTag("SCA Score").performTextInput(scaRating)
        assert(viewModel.scaRating.value == scaRating)
    }

    @Test
    fun otherCoffeeInfoTab_cropHeightInputText_changesViewModelCropHeightField() {
        composeTestRule.onNodeWithTag("Crop Height").assertIsDisplayed()
        val cropHeight = "6000"
        composeTestRule.onNodeWithTag("Crop Height").performTextInput(cropHeight)
        assert(viewModel.cropHeight.value == cropHeight)
    }
}