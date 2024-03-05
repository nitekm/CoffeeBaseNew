package ncodedev.coffeebase.ui.screens.editcoffee

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.components.Screens
import ncodedev.coffeebase.ui.components.topbar.CoffeeBaseTopAppBar
import ncodedev.coffeebase.ui.theme.CoffeeBaseTheme

@Composable
fun EditCoffeeScreen(navController: NavHostController) {

    val tabItems = listOf(
        stringResource(R.string.general),
        stringResource(R.string.origin),
        stringResource(R.string.other)
    )
    var tabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CoffeeBaseTopAppBar(
                titleResId = R.string.edit_coffee,
                canNavigateBack = true,
                navigateUp = { navController.navigate(Screens.MyCoffeeBase.name) }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.coffeebean),
                    contentDescription = stringResource(R.string.coffee_photo),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = stringResource(R.string.tap_to_change_image),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 10.dp)
                    )
                Divider()
                TabRow(
                    selectedTabIndex = tabIndex,
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    tabItems.forEachIndexed { index, title ->
                        Tab(
                            selected = tabIndex == index,
                            onClick = { tabIndex = index },
                            selectedContentColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedContentColor = MaterialTheme.colorScheme.tertiary
                        ) {
                            Text(text = title)
                        }
                    }
                }
                Box(modifier = Modifier.padding(paddingValues)) {
                    when (tabIndex) {
                        0 -> GeneralCoffeeInfo()
//                    1 -> OriginCoffeeInfo(innerPadding)
//                    2 -> OtherCoffeeInfo(innerPadding)
                    }
                }
            }
        })
}

@Composable
fun GeneralCoffeeInfo() {
    TextField(value = , onValueChange = )
}

@Preview(showBackground = true)
@Composable
fun EditCoffeeScreenPreview() {
    CoffeeBaseTheme {
        EditCoffeeScreen(navController = rememberNavController())
    }
}