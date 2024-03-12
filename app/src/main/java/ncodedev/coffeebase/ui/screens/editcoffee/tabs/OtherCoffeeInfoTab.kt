package ncodedev.coffeebase.ui.screens.editcoffee.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.components.common.CoffeeBaseStandardTextField
import ncodedev.coffeebase.ui.screens.editcoffee.EditCoffeeViewModel

@Composable
fun OtherCoffeeInfo(editCoffeeViewModel: EditCoffeeViewModel) {
    Column(modifier = Modifier.padding(all = 20.dp)) {
        CoffeeBaseStandardTextField(
            value = editCoffeeViewModel.processing.value,
            onValueChange = { processing -> editCoffeeViewModel.processing.value = processing},
            labelResId = R.string.processing
        )
        CoffeeBaseStandardTextField(
            value = editCoffeeViewModel.cropHeight.value,
            onValueChange = { cropHeight -> editCoffeeViewModel.validateAndSetCropHeight(Integer.parseInt(cropHeight)) },
            labelResId = R.string.crop_height,
            keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            isValid = editCoffeeViewModel.isCropHeightValid.value,
            validationFailMessage = stringResource(R.string.constraint_crop_height)
        )
        CoffeeBaseStandardTextField(
            value = editCoffeeViewModel.scaRating.value,
            onValueChange = { scaRating -> editCoffeeViewModel.validateAndSetScaRating(Integer.parseInt(scaRating)) },
            labelResId = R.string.sca_score,
            keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            isValid = editCoffeeViewModel.isScaRatingValid.value,
            validationFailMessage = stringResource(R.string.constraint_sca_rating)
        )
    }
}
