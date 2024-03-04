package ncodedev.coffeebase.ui.components.topbar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.screens.mycoffeebase.MyCoffeeBaseViewModel

@Composable
fun SearchAction(
    searchModeActive: MutableState<Boolean>,
    searchQuery: MutableState<String>,
    viewModel: MyCoffeeBaseViewModel
) {
    if (searchModeActive.value) {
        BackHandler {
            searchModeActive.value = false
        }
        Box(modifier = Modifier.fillMaxWidth().padding(5.dp), content = {
            TextField(
                value = searchQuery.value,
                onValueChange = { newValue -> searchQuery.value = newValue },
                placeholder = { Text(text = stringResource(R.string.search_by_anything)) },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.searchCoffees(searchQuery.value)
                        searchModeActive.value = false
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .testTag("SearchTextField"),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.testTag("SearchLeadIcon"),
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_by_anything)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { searchModeActive.value = false },
                        modifier = Modifier.testTag("SearchTrailIcon")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.close)
                        )
                    }
                },
            )
        })
    } else {
        IconButton(onClick = { searchModeActive.value = true }) {
            Icon(
                modifier = Modifier.testTag("SearchActionButton"),
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search)
            )
        }
    }
}