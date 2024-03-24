package ncodedev.coffeebase.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ncodedev.coffeebase.ui.screens.coffeescreen.CoffeeScreen
import ncodedev.coffeebase.ui.screens.editcoffee.EditCoffeeScreen
import ncodedev.coffeebase.ui.screens.login.LoginScreen
import ncodedev.coffeebase.ui.screens.mycoffeebase.MyCoffeeBaseScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.name
    ) {
        composable(Screens.Login.name) {
            LoginScreen(navController)
        }
        composable(Screens.MyCoffeeBase.name) {
            MyCoffeeBaseScreen(navController)
        }
        composable(
            "${Screens.Coffee.name}/{coffeeId}",
            arguments = listOf(navArgument("coffeeId") { type = NavType.LongType })
        ) {
            CoffeeScreen(navController = navController, it.arguments?.getLong("coffeeId") ?: 0L)
        }
        composable(
            "${Screens.EditCoffee.name}/{coffeeId}",
            arguments = listOf(navArgument("coffeeId") {type = NavType.LongType })
            ) {
            EditCoffeeScreen(navController = navController, it.arguments?.getLong("coffeeId") ?: 0L)
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
