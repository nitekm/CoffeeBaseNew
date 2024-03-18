package ncodedev.coffeebase.ui.screens.coffeescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ncodedev.coffeebase.R
import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.ui.components.Screens
import ncodedev.coffeebase.ui.components.topbar.CoffeeBaseTopAppBar
import ncodedev.coffeebase.ui.theme.CoffeeBaseTheme

@Composable
fun CoffeeScreen(navController: NavHostController) {
    var coffee by remember { mutableStateOf<Coffee?>(null) }

    Scaffold(
        topBar = {
            CoffeeBaseTopAppBar(
                titleResId = R.string.coffee,
                canNavigateBack = true,
                navigateUp = { navController.navigate(Screens.MyCoffeeBase.name) },
                actions = {

                }
            )
        },
        content = {paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(coffee?.getImageDownloadUrl())
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.coffee_photo),
                    placeholder = painterResource(R.drawable.coffeebean),
                    error = painterResource(R.drawable.coffeebean),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 6.dp)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCoffeeScreen() {
    CoffeeBaseTheme {
        CoffeeScreen(navController = rememberNavController())
    }
}