package ncodedev.coffeebase.ui.screens.mycoffeebase.component.topbar

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import io.mockk.mockk
import ncodedev.coffeebase.ui.components.topbar.SortAction
import ncodedev.coffeebase.ui.screens.mycoffeebase.MyCoffeeBaseViewModel
import org.junit.Rule
import org.junit.Test

class SortActionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel: MyCoffeeBaseViewModel = mockk()

    @Test
    fun test_sortAction_uiComponents_displayedCorrectly() {
        // Prepare mock MenuToggle interface object
        val menuState = mutableStateOf(false)

        // Set content to display SortAction
        composeTestRule.setContent {
            SortAction(showSortMenu = menuState, viewModel = mockViewModel)
        }

        // Verify IconButton is displayed
        composeTestRule.onNodeWithTag("SortActionButton").assertExists()

        // Assert that DropdownMenu is displayed and its default state is collapsed (not expanded)
        composeTestRule.onNodeWithTag("SortDropdownMenu").assertExists()
        // Menu is not expanded by default, which is indicated by not being able to find opened Options items
        composeTestRule.onNodeWithText("Sort Default").assertDoesNotExist()
    }

}