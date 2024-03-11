package ncodedev.coffeebase.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun CoffeeBaseStandardTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelResId: Int,
    keyBoardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    modifier: Modifier = Modifier.padding(vertical = 5.dp),
    isValid: Boolean = true,
    validationFailMessage: String = ""
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(labelResId)) },
        singleLine = true,
        keyboardOptions = keyBoardOptions,
        isError = !isValid,
        modifier = modifier
    )
    if (!isValid) {
        Text(
            text = validationFailMessage,
            color = Color.Red,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> TextListDropdownMenu(
    expanded: MutableState<Boolean>,
    modifier: Modifier = Modifier.padding(vertical = 5.dp),
    initialValueResId: Int,
    labelResId: Int,
    dropDownItemsList: List<T>,
    populateMenuItemsFunction: @Composable (T) -> Unit
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = stringResource(initialValueResId),
            label = { Text(text = stringResource(labelResId)) },
            onValueChange = { },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            dropDownItemsList.forEach{ item -> populateMenuItemsFunction(item) }
        }
    }
}
