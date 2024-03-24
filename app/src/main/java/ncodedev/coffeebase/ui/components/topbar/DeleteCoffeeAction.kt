package ncodedev.coffeebase.ui.components.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ncodedev.coffeebase.R

@Composable
fun DeleteCoffeeAction(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.delete_coffee)
            )
    }
}