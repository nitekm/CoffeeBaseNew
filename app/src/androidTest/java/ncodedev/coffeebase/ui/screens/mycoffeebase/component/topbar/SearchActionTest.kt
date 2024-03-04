package ncodedev.coffeebase.ui.screens.mycoffeebase.component.topbar

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso.onView
import io.mockk.mockk
import ncodedev.coffeebase.ui.components.topbar.SearchAction
import ncodedev.coffeebase.ui.screens.mycoffeebase.MyCoffeeBaseViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchActionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel: MyCoffeeBaseViewModel = mockk(relaxed = true)

    @Before
    fun setup() {
        val searchModeActive = mutableStateOf(false)
        val searchQuery = mutableStateOf("")

        composeTestRule.setContent {
            SearchAction(searchModeActive = searchModeActive, searchQuery = searchQuery, viewModel = mockViewModel)
        }
    }

    @Test
    fun test_searchAction_searchActionButtonIsVisibleAndSearchBoxIsHidden() {
        composeTestRule.onNodeWithTag("SearchActionButton", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("SearchTextField").assertDoesNotExist()
    }

    @Test
    fun test_searchAction_searchTextFieldIsDisplayedAfterClickingSearchActionButton() {
        composeTestRule.onNodeWithTag("SearchActionButton", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithTag("SearchTextField").assertExists()
    }

    @Test
    fun test_searchTextFieldHasSearchAsIMEAction() {
        onView()
    }
}