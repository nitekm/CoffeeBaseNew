package ncodedev.coffeebase.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ncodedev.coffeebase.R

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
        modifier = modifier.testTag(stringResource(labelResId))
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
        modifier = modifier.testTag(stringResource(labelResId)),
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
            dropDownItemsList.forEach { populateMenuItemsFunction(it) }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    maxRating: Int = 6,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit = {}
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

@Composable
fun DisplayCoffeeInfoCard(
    title: String,
    shouldDisplayCard: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    if (shouldDisplayCard) {
        Card(
            modifier = Modifier.padding(top = 15.dp, start = 40.dp, end = 40.dp),
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ) {
            Column {
                Row(modifier = Modifier.padding(all = 5.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                content()
            }
        }
    }
}

@Composable
fun LabelledText(
    label: String,
    text: String?
) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall)
        Text(text ?: "", style = MaterialTheme.typography.bodyMedium)
    }
}

