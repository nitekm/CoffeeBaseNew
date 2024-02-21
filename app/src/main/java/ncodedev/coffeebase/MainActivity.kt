package ncodedev.coffeebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ncodedev.coffeebase.ui.CoffeeBaseApp
import ncodedev.coffeebase.ui.theme.CoffeeBaseTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeBaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CoffeeBaseApp()
                }
            }
        }
    }
}