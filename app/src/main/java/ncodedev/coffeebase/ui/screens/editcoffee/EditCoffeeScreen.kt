package ncodedev.coffeebase.ui.screens.editcoffee

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.components.Screens
import ncodedev.coffeebase.ui.components.topbar.CoffeeBaseTopAppBar

@Composable
fun EditCoffeeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CoffeeBaseTopAppBar(
                titleResId = R.string.edit_coffee,
                canNavigateBack = true,
                navigateUp = { navController.navigate(Screens.MyCoffeeBase.name) },
                actions = {}
            )
        },
        content = { innerPadding ->
            {

            }
        })
}

@Preview(showBackground = true)
@Composable
fun EditCoffeeScreenPreview() {
    EditCoffeeScreen(navController = rememberNavController())
}