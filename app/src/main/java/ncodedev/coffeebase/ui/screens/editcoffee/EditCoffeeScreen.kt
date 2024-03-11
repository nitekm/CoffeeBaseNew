package ncodedev.coffeebase.ui.screens.editcoffee

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.components.Screens
import ncodedev.coffeebase.ui.components.topbar.CoffeeBaseTopAppBar
import ncodedev.coffeebase.ui.screens.editcoffee.tabs.GeneralCoffeeInfo
import ncodedev.coffeebase.ui.screens.editcoffee.tabs.OriginCoffeeInfo
import ncodedev.coffeebase.ui.screens.editcoffee.tabs.OtherCoffeeInfo
import ncodedev.coffeebase.ui.theme.CoffeeBaseTheme

@Composable
fun EditCoffeeScreen(navController: NavHostController) {

    val tabItems = listOf(
        stringResource(R.string.general),
        stringResource(R.string.origin),
        stringResource(R.string.other)
    )
    var tabIndex by remember { mutableIntStateOf(0) }
    val editCoffeeViewModel: EditCoffeeViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            CoffeeBaseTopAppBar(
                titleResId = R.string.edit_coffee,
                canNavigateBack = true,
                navigateUp = { navController.navigate(Screens.MyCoffeeBase.name) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoffeeImageFromGallery(modifier = Modifier.padding(top = 15.dp))
                //TODO gdzies tutaj jakis plusik maly ktory odpali dialog z mozliwoscia dodania tagow
                // plusik powinien byc na dole atagi powinny pojawiac sie nad plusikiem najlepiej zawijac sie
                Text(
                    text = stringResource(R.string.tap_to_change_image),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                HorizontalDivider()
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
                Box {
                    when (tabIndex) {
                        0 -> GeneralCoffeeInfo(editCoffeeViewModel)
                        1 -> OriginCoffeeInfo(editCoffeeViewModel)
                        2 -> OtherCoffeeInfo(editCoffeeViewModel)
                    }
                }
            }
            Button(
                onClick = { editCoffeeViewModel.saveCoffee() },
                enabled = editCoffeeViewModel.isNameValid.value,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 30.dp)
            ) {
                Text(
                    text = stringResource(R.string.save),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 40.dp, vertical = 5.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditCoffeeScreenPreview() {
    CoffeeBaseTheme {
        EditCoffeeScreen(navController = rememberNavController())
    }
}
