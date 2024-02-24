package ncodedev.coffeebase.ui.screens.mycoffeebase

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import ncodedev.coffeebase.model.Coffee
import org.junit.Rule
import org.junit.Test


class MyCoffeeBaseScreenUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun coffee_card_is_populated_with_coffee_data() {
        val coffee = Coffee(1, "coffee name", true,"test image url")
        composeTestRule.setContent {
            CoffeeCard(coffee)
        }

        composeTestRule.onNodeWithText("coffee name").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Favourite icon").assertExists()
    }

    @Test
    fun given_10coffees_grid_have_10elements() {
        val coffees = List(5) {
            Coffee(it.toLong(), "coffee name $it", false, "test image url $it")
        }

        composeTestRule.setContent {
            CoffeesGrid(coffees = coffees, contentPadding = PaddingValues(1.dp))
        }

        composeTestRule.onAllNodesWithText("coffee name", substring = true, ignoreCase = true).assertCountEquals(5)
    }

//    @Test
//    fun when_menu_icon_is_clicked_nav_drawer_opens() {
//
//        composeTestRule.setContent {
//            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//            val scope = rememberCoroutineScope()
//
//            CoffeeBaseTopAppBar(
//                titleResId = R.string.my_coffeebase,
//                canShowNavigationDrawerIcon = true,
//                showNavigationDrawer = { scope.launch { drawerState.open() } },
//                canNavigateBack = false,
//                navigateUp = { },
//                actions = {}
//            )
//            MyCoffeeBaseNavigationDrawer(
//                currentRoute = "Test",
//                navController = NavHostController(LocalContext.current),
//                scope = scope,
//                drawerState = drawerState
//            ) {
//
//            }
//        }
//        composeTestRule.onNodeWithContentDescription("Menu Button").performClick()
//        composeTestRule.waitForIdle()
//        composeTestRule.onNodeWithContentDescription("User picture").assertIsDisplayed()
//
//    }
}
