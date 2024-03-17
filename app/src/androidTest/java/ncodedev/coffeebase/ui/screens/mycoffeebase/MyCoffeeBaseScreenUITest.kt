package ncodedev.coffeebase.ui.screens.mycoffeebase

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import ncodedev.coffeebase.createTestCoffee
import org.junit.Rule
import org.junit.Test

class MyCoffeeBaseScreenUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun coffee_card_is_populated_with_coffee_data() {
        val coffee = createTestCoffee(id = 1, name =  "coffee name", favourite = true)
        composeTestRule.setContent {
            CoffeeCard(coffee)
        }

        composeTestRule.onNodeWithText("coffee name").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Favourite icon").assertExists()
    }

    @Test
    fun given_10coffees_grid_have_10elements() {
        val coffees = List(5) {
            createTestCoffee(id = it.toLong(), name = "coffee name $it")
        }

        composeTestRule.setContent {
            CoffeesGrid(coffees = coffees, contentPadding = PaddingValues(1.dp))
        }

        composeTestRule.onAllNodesWithText("coffee name", substring = true, ignoreCase = true).assertCountEquals(5)
    }
}
