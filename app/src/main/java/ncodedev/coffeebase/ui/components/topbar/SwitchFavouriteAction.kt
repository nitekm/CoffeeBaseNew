package ncodedev.coffeebase.ui.components.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.screens.coffeescreen.CoffeeViewModel

@Composable
fun SwitchFavouriteAction(coffeeViewModel: CoffeeViewModel) {
    IconButton(onClick = { coffeeViewModel.switchFavourite() }) {
        if (coffeeViewModel.coffee.value?.favourite == false) {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = stringResource(R.string.favourite_icon))
        } else {
            Icon(painter = painterResource(R.drawable.ic_favorite_filled), contentDescription = stringResource(R.string.favourite_icon))
        }
    }
}