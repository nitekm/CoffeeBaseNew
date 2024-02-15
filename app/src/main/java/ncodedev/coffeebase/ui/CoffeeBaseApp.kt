@file:OptIn(ExperimentalMaterial3Api::class)

package ncodedev.coffeebase.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ncodedev.coffeebase.ui.screens.mycoffeebase.MyCoffeeBaseScreen

@Composable
fun CoffeeBaseApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {innerPadding ->
        MyCoffeeBaseScreen()
    }
}

