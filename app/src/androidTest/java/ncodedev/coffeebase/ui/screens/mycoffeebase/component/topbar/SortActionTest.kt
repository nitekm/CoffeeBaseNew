package ncodedev.coffeebase.ui.screens.mycoffeebase.component.topbar

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import io.mockk.mockk
import io.mockk.verify
import ncodedev.coffeebase.ui.components.topbar.SortAction
import ncodedev.coffeebase.ui.screens.mycoffeebase.MyCoffeeBaseViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SortActionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel: MyCoffeeBaseViewModel = mockk(relaxed = true)

    @Before
    fun setup() {
        val sortMenuState = mutableStateOf(false)

        composeTestRule.setContent {
            SortAction(showSortMenu = sortMenuState, viewModel = mockViewModel)
        }
    }

    @Test
    fun test_sortAction_existsAndMenuIsHidden() {
        //SortActionMenu exists and is hidden: Icon is visible, and sort options are not visible
        composeTestRule.onNodeWithTag("SortActionButton").assertExists()
        composeTestRule.onNodeWithText("Sort Default").assertDoesNotExist()
    }

    @Test
    fun test_sortAction_opensOnClickAndAllElementsAreVisible() {
        composeTestRule.onNodeWithTag("SortActionButton").performClick()
        composeTestRule.onNodeWithTag("SortTitleMenuItem").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SortDefaultMenuItem").assertIsDisplayed()
        composeTestRule.onAllNodesWithTag("SortOption").assertCountEquals(6)
    }

    @Test
    fun test_sortAction_onClickOnDefaultViewModelFetchInitDataIsCalled() {
        composeTestRule.onNodeWithTag("SortActionButton").performClick()
        composeTestRule.onNodeWithTag("SortDefaultMenuItem").performClick()

        verify { mockViewModel.fetchInitData() }
    }

    @Test
    fun test_sortAction_onClickOnSortOptionViewModelFetchSortedIsCalled() {
        composeTestRule.onNodeWithTag("SortActionButton").performClick()
        val nodes = composeTestRule.onAllNodesWithTag("SortOption", useUnmergedTree = true)
        if (nodes.fetchSemanticsNodes().isNotEmpty()) {
            nodes.onFirst().performClick()
        }

        verify { mockViewModel.fetchSorted(any()) }
    }
}
