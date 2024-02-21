package ncodedev.coffeebase.ui.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ncodedev.coffeebase.ui.screens.editcoffee.EditCoffeeScreen
import ncodedev.coffeebase.ui.screens.login.LoginScreen
import ncodedev.coffeebase.ui.screens.mycoffeebase.MyCoffeeBaseScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.name
    ) {
        composable(Screens.MyCoffeeBase.name) {
            Log.d("APPNAVHOST", "navigate to MyCoffeeBaseScreen")
            MyCoffeeBaseScreen(navController)
        }
        composable(Screens.Login.name) {
            LoginScreen(navController)
        }
        composable(Screens.EditCoffee.name) {
            Log.d("APPNAVHOST", "navigate to EDITCOffeeScreen")
            EditCoffeeScreen(navController)
        }
        composable(Screens.AddBrewRecipe.name) {
            //AddBrewRecipeScreen()
        }
        composable(Screens.Account.name) {
            //AccountScreen()
        }
        composable(Screens.About.name) {
            //AboutScreen()
        }
        composable(Screens.Settings.name) {
            //SettingsScreen()
        }
        composable(Screens.SignOut.name) {
            //SignOut()
        }
    }
}
