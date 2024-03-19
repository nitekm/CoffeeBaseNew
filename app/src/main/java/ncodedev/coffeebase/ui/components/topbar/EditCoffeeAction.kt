package ncodedev.coffeebase.ui.components.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.components.Screens

@Composable
fun EditCoffeeAction(navHostController: NavHostController) {
    IconButton(onClick = { navHostController.navigate(Screens.EditCoffee.name) }) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = stringResource(R.string.edit_coffee_button)
        )
    }
}