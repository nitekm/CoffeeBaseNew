package ncodedev.coffeebase.ui.components.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.screens.coffeescreen.CoffeeViewModel

@Composable
fun DeleteCoffeeAction(coffeeViewModel: CoffeeViewModel) {
    IconButton(onClick = { coffeeViewModel.deleteCoffee() }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.delete_coffee)
            )
    }
}