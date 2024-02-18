package ncodedev.coffeebase.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeBaseTopAppBar(
    @StringRes titleResId: Int,
    canShowNavigationDrawerIcon: Boolean = false,
    showNavigationDrawer: () -> Unit = {},
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(titleResId)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
        modifier = modifier,
        navigationIcon = {
                         if (canShowNavigationDrawerIcon) {
                             IconButton(onClick = showNavigationDrawer) {
                                 Icon(
                                     imageVector = Icons.Filled.Menu,
                                     contentDescription = "Menu Button"
                                 )
                             }
                         } else if (canNavigateBack) {
                             IconButton(onClick = navigateUp) {
                                 Icon(
                                     imageVector = Icons.Filled.ArrowBack,
                                     contentDescription = "Back Button"
                                 )
                             }
                         }
        },
        actions = actions
        )
}