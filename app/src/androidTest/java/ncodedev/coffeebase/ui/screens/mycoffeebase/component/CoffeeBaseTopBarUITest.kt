//package ncodedev.coffeebase.ui.screens.mycoffeebase.component
//
//import androidx.compose.material3.DrawerValue
//import androidx.compose.material3.rememberDrawerState
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithContentDescription
//import androidx.compose.ui.test.performClick
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.compose.rememberNavController
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.test.StandardTestDispatcher
//import ncodedev.coffeebase.ui.components.navdrawer.MyCoffeeBaseNavigationDrawer
//import ncodedev.coffeebase.ui.components.navdrawer.NavDrawerViewModel
//import org.junit.Rule
//import org.junit.Test
//
//@HiltAndroidTest
//class CoffeeBaseTopBarUITest {
//
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun navigationDrawerOpenOnMenuClick() {
//        //Provide the test Dispatcher to run asynchronous test synchronously
//        val testDispatcher = StandardTestDispatcher()
//        //Set up the Coroutine environment
//        val coroutineScope = CoroutineScope(testDispatcher)
//
//        composeTestRule.setContent {
//            val testNavController = rememberNavController()
//            // Provide NavDrawerViewModel
//            val viewModel = hiltViewModel<NavDrawerViewModel>()
//            // Set up DrawerState
//            val drawerState = rememberDrawerState(DrawerValue.Closed)
//
//            //Provide the TestNavHostController
////            testNavController.setGraph(R.navigation.nav_graph)
//            MyCoffeeBaseNavigationDrawer(
//                navController = testNavController,
//                currentRoute = "",
//                scope = coroutineScope,
//                drawerState = drawerState,
//                viewModel = viewModel
//            ) {
//                // Your content here, or it could be your main NavGraph
//            }
//
//            //Click menu icon
//            composeTestRule.onNodeWithContentDescription("Menu Button").performClick()
//            //Check if navigation drawer is open
//            composeTestRule.onNodeWithContentDescription("User Image").assertIsDisplayed()
//        }
//    }
//
//
//}
