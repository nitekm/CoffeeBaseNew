package ncodedev.coffeebase.ui.screens.editcoffee

import android.util.Log
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ncodedev.coffeebase.ui.components.Screens

@Composable
fun EditCoffeeScreen(navController: NavHostController) {
    Surface {
        Log.d("EDITOCFFEESCREEN", "EditCoffeeScreen launched")
        navController.navigate(Screens.MyCoffeeBase.name)
    }
}