package ncodedev.coffeebase.ui.screens.editcoffee.tabs

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import ncodedev.coffeebase.data.repository.CoffeeRepository
import ncodedev.coffeebase.model.enums.Continent
import ncodedev.coffeebase.ui.screens.editcoffee.EditCoffeeViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OriginCoffeeInfoTabTest {
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
            OriginCoffeeInfo(editCoffeeViewModel = viewModel)
        }
    }

    @Test
    fun originCoffeeInfoTab_continentDropdown_DisplaysAllOptionsWhenClicked() {
        composeTestRule.onNodeWithTag("Continent").performClick()
        composeTestRule.onNodeWithText(Continent.AFRICA.continentValue, ignoreCase = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(Continent.SOUTH_AMERICA.continentValue, ignoreCase = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(Continent.ASIA.continentValue, ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun originCoffeeInfoTab_continentDropdown_onOptionClickChangesViewModelContinentValue() {
        composeTestRule.onNodeWithTag("Continent").performClick()
        composeTestRule.onNodeWithText(Continent.ASIA.continentValue, ignoreCase = true).performClick()

        assert(viewModel.continent.value == Continent.ASIA)
    }
    @Test
    fun originCoffeeInfoTab_originInputText_changesViewModelOriginField() {
        composeTestRule.onNodeWithTag("Origin").assertIsDisplayed()
        val origin = "test origin"
        composeTestRule.onNodeWithTag("Origin").performTextInput(origin)
        assert(viewModel.origin.value == origin)
    }

    @Test
    fun originCoffeeInfoTab_regionInputText_changesViewModelRegionField() {
        composeTestRule.onNodeWithTag("Region").assertIsDisplayed()
        val region = "test region"
        composeTestRule.onNodeWithTag("Region").performTextInput(region)
        assert(viewModel.region.value == region)
    }

    @Test
    fun originCoffeeInfoTab_farmInputText_changesViewModelFarmField() {
        composeTestRule.onNodeWithTag("Farm").assertIsDisplayed()
        val farm = "test farm"
        composeTestRule.onNodeWithTag("Farm").performTextInput(farm)
        assert(viewModel.farm.value == farm)
    }
}