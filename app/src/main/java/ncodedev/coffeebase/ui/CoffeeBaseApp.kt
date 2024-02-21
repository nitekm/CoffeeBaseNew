package ncodedev.coffeebase.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ncodedev.coffeebase.ui.components.AppNavHost

@Composable
fun CoffeeBaseApp() {


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        AppNavHost(navController = rememberNavController())
    }
}

