package ncodedev.coffeebase.ui.screens.editcoffee.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ncodedev.coffeebase.R
import ncodedev.coffeebase.model.enums.RoastProfile
import ncodedev.coffeebase.ui.components.common.CoffeeBaseStandardTextField
import ncodedev.coffeebase.ui.components.common.TextListDropdownMenu
import ncodedev.coffeebase.ui.screens.editcoffee.EditCoffeeViewModel

@Composable
fun GeneralCoffeeInfo(editCoffeeViewModel: EditCoffeeViewModel) {

    val roastProfileDropdownState = remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.padding(all = 20.dp)) {
        CoffeeBaseStandardTextField(
            value = editCoffeeViewModel.coffeeName.value,
            onValueChange = { coffeeName -> editCoffeeViewModel.coffeeName.value = coffeeName},
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
        CoffeeBaseStandardTextField(
            value = editCoffeeViewModel.roaster.value,
            onValueChange = { roaster -> editCoffeeViewModel.roaster.value = roaster },
            labelResId = R.string.roaster
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
