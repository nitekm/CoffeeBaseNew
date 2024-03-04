package ncodedev.coffeebase.ui.components.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ncodedev.coffeebase.R
import ncodedev.coffeebase.model.SortOptions
import ncodedev.coffeebase.ui.screens.mycoffeebase.MyCoffeeBaseViewModel


@Composable
fun SortAction(
    showSortMenu: MutableState<Boolean>,
    viewModel: MyCoffeeBaseViewModel
) {
    IconButton(
        onClick = { showSortMenu.value = true },
        Modifier.testTag("SortActionButton")
    ) {
        Icon(
            painter = painterResource(R.drawable.sort_filled_24),
            contentDescription = stringResource(R.string.sort)
        )
    }
    DropdownMenu(
        expanded = showSortMenu.value,
        onDismissRequest = { showSortMenu.value = false },
        Modifier.testTag("SortDropdownMenu")
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.sort),
                    color = Color.Gray,
                    fontSize = 17.sp
                )
            },
            onClick = {},
            modifier = Modifier.testTag("SortTitleMenuItem")
        )
        Divider()
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.sort_default)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = stringResource(R.string.sort_default)
                )
            },
            onClick = { viewModel.fetchInitData() },
            modifier = Modifier.testTag("SortDefaultMenuItem")
        )
        SortOptions.entries.forEach { sortOption ->
            SortMenuItem(
                sortOptions = sortOption,
                onSortOptionsSelected = { viewModel.fetchSorted(sortOption) }
            )
        }
    }
}

@Composable
private fun SortMenuItem(sortOptions: SortOptions, onSortOptionsSelected: () -> Unit) {
    DropdownMenuItem(
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(sortOptions.contentDescitpionResId),
                modifier = Modifier.rotate(sortOptions.iconRotateValue)
            )
        },
        text = { Text(text = stringResource(sortOptions.nameResId)) },
        onClick = onSortOptionsSelected,
        modifier = Modifier.testTag("SortOption")
    )
}
