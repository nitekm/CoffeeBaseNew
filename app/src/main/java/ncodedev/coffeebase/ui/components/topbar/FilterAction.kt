package ncodedev.coffeebase.ui.components.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ncodedev.coffeebase.R
import ncodedev.coffeebase.model.FilterOptions
import ncodedev.coffeebase.ui.screens.mycoffeebase.MyCoffeeBaseViewModel
import java.util.*

@Composable
fun FilterAction(showFilterMenu: MutableState<Boolean>,
                 viewModel: MyCoffeeBaseViewModel
) {
    IconButton(onClick = { showFilterMenu.value = true }) {
        Icon(
            painter = painterResource(R.drawable.filter_filled_24),
            contentDescription = stringResource(R.string.do_filter)
        )
    }
    FilterMenu(showFilterMenu, viewModel)
}

@Composable
private fun FilterMenu(
    showFilterMenu: MutableState<Boolean>,
    viewModel: MyCoffeeBaseViewModel
) {

    val filterStates = remember { mutableStateMapOf<FilterOptions, Boolean>() }

    FilterOptions.entries.forEach{ filterOption ->
        filterStates[filterOption] = viewModel.currentFilters.value?.get(filterOption.filterKey)?.contains(filterOption.filterValue) ?: false}

    DropdownMenu(
        expanded = showFilterMenu.value,
        onDismissRequest = { showFilterMenu.value = false },
    ) {
        FilterOptions.entries.groupBy { it.filterKey }.forEach { (filterKey, filterOptions) ->
            Text(
                text = filterKey.replaceFirstChar { it.titlecase(Locale.getDefault()) },
                modifier = Modifier.padding(7.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            filterOptions.forEach { filterOption ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            filterStates[filterOption] = filterStates[filterOption]?.not() ?: true
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = filterStates[filterOption] ?: false,
                        onCheckedChange = null
                    )
                    Text(
                        text = stringResource(filterOption.displayNameResId),
                        modifier = Modifier.padding(start = 7.dp)
                    )
                }
            }
            Divider()
        }
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.apply_filters)) },
            onClick = {
                val chosenFilters = filterStates.entries.filter {
                    it.value
                }.groupBy({ it.key.filterKey }, { it.key.filterValue } ).mapValues { it.value.toSet() }
                viewModel.fetchFiltered(chosenFilters)
                showFilterMenu.value = false
            },
            colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.tertiaryContainer)
        )
    }
}