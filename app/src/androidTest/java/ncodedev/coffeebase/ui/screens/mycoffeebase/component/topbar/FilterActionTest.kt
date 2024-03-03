package ncodedev.coffeebase.ui.screens.mycoffeebase.component.topbar

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import io.mockk.mockk
import ncodedev.coffeebase.ui.components.topbar.FilterAction
import ncodedev.coffeebase.ui.components.topbar.SortAction
import ncodedev.coffeebase.ui.screens.mycoffeebase.MyCoffeeBaseViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FilterActionTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel: MyCoffeeBaseViewModel = mockk(relaxed = true)

    @Before
    fun setup() {
        val filterMenuState = mutableStateOf(false)

        composeTestRule.setContent {
            FilterAction(showFilterMenu = filterMenuState, viewModel = mockViewModel)
        }
    }

    @Test
    fun test_filterAction_existsAndMenuIsHidden() {

    }

    @Test
    fun test_filterAction_opensOnClickAndAllElementsAreVisible() {
    }

    @Test
    fun test_filterAction_onClickOnApplyFiltersSelectedFiltersArePassedToViewModel() {

    }
}