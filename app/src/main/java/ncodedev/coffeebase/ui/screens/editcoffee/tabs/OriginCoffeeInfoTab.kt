package ncodedev.coffeebase.ui.screens.editcoffee.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ncodedev.coffeebase.R
import ncodedev.coffeebase.model.enums.Continent
import ncodedev.coffeebase.ui.components.CoffeeBaseStandardTextField
import ncodedev.coffeebase.ui.components.TextListDropdownMenu
import ncodedev.coffeebase.ui.screens.editcoffee.EditCoffeeViewModel
import ncodedev.coffeebase.ui.theme.CoffeeBaseTheme


@Composable
fun OriginCoffeeInfo(editCoffeeViewModel: EditCoffeeViewModel) {
    val continentDropdownState = remember { mutableStateOf(false) }
    
    Column(modifier = Modifier.padding(all = 20.dp)) {
        TextListDropdownMenu(
            expanded = continentDropdownState,
            initialValueResId = Continent.CONTINENT.continentResId,
            labelResId = R.string.continent,
            dropDownItemsList = Continent.entries,
            populateMenuItemsFunction = { continent ->
                DropdownMenuItem(
                    text = { stringResource(continent.continentResId) },
                    onClick = {
                        editCoffeeViewModel.continent.value = continent
                        continentDropdownState.value = false
                    }
                )
            }
        )
        CoffeeBaseStandardTextField(
            value = editCoffeeViewModel.origin.value,
            onValueChange = { origin -> editCoffeeViewModel.origin.value = origin },
            labelResId = R.string.origin,
            keyBoardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        CoffeeBaseStandardTextField(
            value = editCoffeeViewModel.region.value,
            onValueChange = { region -> editCoffeeViewModel.region.value = region },
            labelResId = R.string.region,
            keyBoardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        CoffeeBaseStandardTextField(
            value = editCoffeeViewModel.farm.value,
            onValueChange = { farm -> editCoffeeViewModel.farm.value = farm },
            labelResId = R.string.farm,
            keyBoardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOriginCoffeeInfo() {
    val editCoffeeViewModel: EditCoffeeViewModel = hiltViewModel()

    CoffeeBaseTheme {
        OriginCoffeeInfo(editCoffeeViewModel)
    }
}
