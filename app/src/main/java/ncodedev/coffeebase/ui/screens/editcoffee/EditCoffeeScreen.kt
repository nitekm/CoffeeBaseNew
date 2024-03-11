package ncodedev.coffeebase.ui.screens.editcoffee

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ncodedev.coffeebase.R
import ncodedev.coffeebase.model.enums.RoastProfile
import ncodedev.coffeebase.ui.components.CoffeeBaseStandardTextField
import ncodedev.coffeebase.ui.components.Screens
import ncodedev.coffeebase.ui.components.TextListDropdownMenu
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
//                    1 -> OriginCoffeeInfo(innerPadding)
//                    2 -> OtherCoffeeInfo(innerPadding)
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


@Composable
fun GeneralCoffeeInfo(editCoffeeViewModel: EditCoffeeViewModel) {

    val roastProfileDropdownState = remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.padding(all = 20.dp)) {
        CoffeeBaseStandardTextField(
            value = editCoffeeViewModel.coffeeName.value,
            onValueChange = { coffeeName -> editCoffeeViewModel.validateAndSetCoffeeName(coffeeName)},
            labelResId = R.string.coffee_name,
            keyBoardOptions = KeyboardOptions.Default,
            isValid = editCoffeeViewModel.isNameValid.value,
            validationFailMessage = stringResource(R.string.constraint_coffee_name_not_empty)
        )
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 7.dp)
        ) {
            Text(
                text = stringResource(R.string.rating),
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 10.dp, top = 10.dp)
            )
            RatingBar(
                modifier = Modifier.padding(vertical = 5.dp),
                currentRating = editCoffeeViewModel.rating.doubleValue.toInt(),
                onRatingChanged = { newRating -> editCoffeeViewModel.rating.doubleValue = newRating.toDouble() }
            )
        }
        TextField(
            value = editCoffeeViewModel.roaster.value,
            onValueChange = { roaster -> editCoffeeViewModel.roaster.value = roaster },
            label = { Text(text = stringResource(R.string.roaster)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.padding(vertical = 5.dp)

        )
        TextListDropdownMenu(
            expanded = roastProfileDropdownState,
            initialValueResId = RoastProfile.ROAST_PROFILE.roastProfileResId,
            labelResId = R.string.roast_profile,
            dropDownItemsList = RoastProfile.entries,
            populateMenuItemsFunction = { roastProfile ->
                DropdownMenuItem(
                    text = { stringResource(roastProfile.roastProfileResId) },
                    onClick = {
                        editCoffeeViewModel.roastProfile.value = roastProfile
                        roastProfileDropdownState.value = false
                    }
                )
            }
        )
    }
}

@Composable
fun OriginCoffeeInfo() {

}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    maxRating: Int = 6,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit
) {
    Row(modifier = modifier) {
        for (i in 1..maxRating) {
            Icon(
                painter = painterResource(id = if (i <= currentRating) R.drawable.star_filled else R.drawable.star_not_filled),
                contentDescription = "Star $i of $maxRating",
                modifier = Modifier
                    .clickable { onRatingChanged(i) }
                    .size(30.dp),
                tint = MaterialTheme.colorScheme.primary
            )
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
