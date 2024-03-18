package ncodedev.coffeebase.ui.screens.editcoffee.tabs

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.model.enums.RoastProfile
import ncodedev.coffeebase.ui.screens.editcoffee.EditCoffeeViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GeneralCoffeeInfoTabTest {

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
            GeneralCoffeeInfo(editCoffeeViewModel = viewModel)
        }
    }

    @Test
    fun generalCoffeeInfoTab_coffeeNameInputText_changesViewModelCoffeeNameField() {
        composeTestRule.onNodeWithTag("Coffee Name").assertIsDisplayed()
        val coffeeName = "test coffee name"
        composeTestRule.onNodeWithTag("Coffee Name").performTextInput(coffeeName)
        assert(viewModel.coffeeName.value == coffeeName)
    }

    @Test
    fun generalCoffeeInfoTab_roasterInputText_changesViewModelRoasterField() {
        composeTestRule.onNodeWithTag("Roaster").assertIsDisplayed()
        val roaster = "test roaster"
        composeTestRule.onNodeWithTag("Roaster").performTextInput(roaster)
        assert(viewModel.roaster.value == roaster)
    }

    @Test
    fun generalCoffeeInfoTab_ratingBarClickOn3rdStar_changesViewModelRatingField() {
        val newRating = 3
        composeTestRule.onNodeWithContentDescription("Star $newRating of 6").performClick()
        assert(viewModel.rating.doubleValue == newRating.toDouble())
    }

    @Test
    fun generalCoffeeInfoTab_ratingBarClickOn6stStar_changesViewModelRatingField() {
        val newRating = 6
        composeTestRule.onNodeWithContentDescription("Star $newRating of 6").performClick()
        assert(viewModel.rating.doubleValue == newRating.toDouble())
    }

    @Test
    fun generalCoffeeInfoTab_roastProfileDropdown_DisplaysAllOptionsWhenClicked() {
        composeTestRule.onNodeWithTag("Roast Profile").performClick()
        composeTestRule.onNodeWithText(RoastProfile.LIGHT.roastProfileValue, ignoreCase = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(RoastProfile.DARK.roastProfileValue, ignoreCase = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(RoastProfile.OMNIROAST.roastProfileValue, ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun generalCoffeeInfoTab_roastProfileDropdown_onOptionClickChangesViewModelRoastProfileValue() {
        composeTestRule.onNodeWithTag("Roast Profile").performClick()
        composeTestRule.onNodeWithText(RoastProfile.LIGHT.roastProfileValue, ignoreCase = true).performClick()

        assert(viewModel.roastProfile.value == RoastProfile.LIGHT)
    }
}