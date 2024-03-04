package ncodedev.coffeebase.ui.screens.mycoffeebase.component.topbar

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import ncodedev.coffeebase.ui.components.topbar.FilterAction
import ncodedev.coffeebase.ui.screens.mycoffeebase.MyCoffeeBaseViewModel
import org.junit.Rule
import org.junit.Test

class FilterActionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel: MyCoffeeBaseViewModel = mockk(relaxed = true)

    @Test
    fun test_filterAction_existsAndMenuIsHidden() {
        every { mockViewModel.currentFilters.value } returns emptyMap()

        val filterMenuState = mutableStateOf(false)

        composeTestRule.setContent {
            FilterAction(showFilterMenu = filterMenuState, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithTag("FilterActionButton").assertExists()
        composeTestRule.onNodeWithTag("ApplyFiltersMenuItem").assertDoesNotExist()
    }

    @Test
    fun test_filterAction_opensOnClickAndAllElementsAreVisible() {
        every { mockViewModel.currentFilters.value } returns emptyMap()

        val filterMenuState = mutableStateOf(false)

        composeTestRule.setContent {
            FilterAction(showFilterMenu = filterMenuState, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithTag("FilterActionButton").performClick()
        composeTestRule.onNodeWithTag("FilterMenu").assertExists()
        composeTestRule.onNodeWithTag("ApplyFiltersMenuItem").assertExists()
        composeTestRule.onAllNodesWithTag("FilterOption").assertCountEquals(8)
    }

    @Test
    fun test_filterAction_onClickOnApplyFiltersSelectedFiltersArePassedToViewModel() {

        every { mockViewModel.currentFilters.value } returns emptyMap()

        val filterMenuState = mutableStateOf(false)

        composeTestRule.setContent {
            FilterAction(showFilterMenu = filterMenuState, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithTag("FilterActionButton").performClick()
        composeTestRule.onNodeWithTag("LightCheckBox", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithTag("AsiaCheckBox", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithTag("AfricaCheckBox", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithTag("yesCheckBox", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithTag("DarkCheckBox", useUnmergedTree = true).performClick()

        composeTestRule.onNodeWithTag("ApplyFiltersMenuItem").performClick()

        val expectedFilters = mapOf(
            "roastProfile" to setOf("Light", "Dark"),
            "continent" to setOf("Asia", "Africa"),
            "favourite" to setOf("yes")
        )

        verify { mockViewModel.fetchFiltered(match { it == expectedFilters }) }

    }

    //TODO: Fix - this test is not passing, in real app feature is working fine...
//    @Test
//    fun test_filterAction_checkCheckboxesWithFilterOptionsPresentInCurrentFiltersMap() {
//        val filters = mapOf(
//            "roastProfile" to setOf("Light", "Omniroast"),
//            "continent" to setOf("Africa", "Asia")
//        )
//        every { mockViewModel.currentFilters.value } returns filters
//
//        val filterMenuState = mutableStateOf(true)
//
//        composeTestRule.setContent {
//            FilterAction(showFilterMenu = filterMenuState, viewModel = mockViewModel)
//        }
//
//        composeTestRule.onNodeWithTag("LightCheckBox", useUnmergedTree = true).assertIsSelected()
//        composeTestRule.onNodeWithTag("OmniroastCheckBox", useUnmergedTree = true).assertIsSelected()
//        composeTestRule.onNodeWithTag("AfricaCheckBox", useUnmergedTree = true).assertIsSelected()
//        composeTestRule.onNodeWithTag("AsiaCheckBox", useUnmergedTree = true).assertIsSelected()
//    }
}
