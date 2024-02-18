package ncodedev.coffeebase.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ncodedev.coffeebase.R

data class NavigationItem(
    val title: String,
    val unselectedIcon: ImageVector
)

@Composable
fun MyCoffeeBaseNavigationDrawer(selectedItemIndex: MutableState<Int>, scope: CoroutineScope, drawerState: DrawerState, content: @Composable () -> Unit) {
    val items = getNavigationDrawerItems()
    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {
            items.forEachIndexed { index, item ->
                NavigationDrawerItem(
                    label = { Text(item.title) },
                    selected = selectedItemIndex.value == index,
                    icon = { Icon(
                        imageVector = item.unselectedIcon,
                        contentDescription = item.title
                    ) },
                    onClick = {
                        selectedItemIndex.value = index
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    },
        drawerState = drawerState
    ) {
        content()
    }
}

@Composable
fun getNavigationDrawerItems(): List<NavigationItem> {
    return listOf(
        NavigationItem(
            stringResource(R.string.add_coffee),
            Icons.Filled.AddCircle
        ),
        NavigationItem(
            stringResource(R.string.add_brew_recipe),
            Icons.Filled.AddCircle
        ),
        NavigationItem(
            stringResource(R.string.about),
            Icons.Filled.Info
        ),
        NavigationItem(
            stringResource(R.string.account),
            Icons.Filled.AccountCircle
        ),
        NavigationItem(
            stringResource(R.string.settings),
            Icons.Filled.Settings
        ),
        NavigationItem(
            stringResource(R.string.sign_out),
            Icons.Filled.Lock
        ),
    )
}

