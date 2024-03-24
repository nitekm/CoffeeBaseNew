package ncodedev.coffeebase.ui.components.navdrawer

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.components.Screens

data class NavigationItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun MyCoffeeBaseNavigationDrawer(
    navController: NavController,
    currentRoute: String,
    scope: CoroutineScope,
    drawerState: DrawerState,
    viewModel: NavDrawerViewModel,
    content: @Composable () -> Unit
) {
    val items = getNavigationDrawerItems()
    ModalNavigationDrawer(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        drawerContent = {
            ModalDrawerSheet {
                NavDrawerHeader(viewModel)
                Divider(
                    thickness = 3.dp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                items.forEach{ item ->
                    NavigationDrawerItem(
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(item.route)
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
            "${Screens.EditCoffee.name}/0L",
            Icons.Filled.AddCircle
        ),
        NavigationItem(
            stringResource(R.string.add_brew_recipe),
            Screens.AddBrewRecipe.name,
            Icons.Filled.AddCircle
        ),
        NavigationItem(
            stringResource(R.string.about),
            Screens.About.name,
            Icons.Filled.Info
        ),
        NavigationItem(
            stringResource(R.string.account),
            Screens.Account.name,
            Icons.Filled.AccountCircle
        ),
        NavigationItem(
            stringResource(R.string.settings),
            Screens.Settings.name,
            Icons.Filled.Settings
        ),
        NavigationItem(
            stringResource(R.string.sign_out),
            Screens.SignOut.name,
            Icons.Filled.Lock
        ),
    )
}

@Composable
private fun NavDrawerHeader(viewModel: NavDrawerViewModel) {
    val user by remember {
        mutableStateOf(viewModel.user)
    }
    Log.d("NavigationDrawer", "composed with user: ${user?.username}")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .size(110.dp)
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(user?.pictureUri)
                .transformations(CircleCropTransformation())
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.user_picture),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            error = painterResource(R.drawable.ic_launcher_foreground),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Text(
            text = user?.username ?: "",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

