package ncodedev.coffeebase.ui.screens.mycoffeebase

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import ncodedev.coffeebase.R
import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.ui.components.Screens
import ncodedev.coffeebase.ui.components.navdrawer.MyCoffeeBaseNavigationDrawer
import ncodedev.coffeebase.ui.components.navdrawer.NavDrawerViewModel
import ncodedev.coffeebase.ui.components.topbar.CoffeeBaseTopAppBar
import ncodedev.coffeebase.ui.components.topbar.FilterAction
import ncodedev.coffeebase.ui.components.topbar.SearchAction
import ncodedev.coffeebase.ui.components.topbar.SortAction

@Composable
fun MyCoffeeBaseScreen(navController: NavHostController) {

    val coffeeBaseViewModel: MyCoffeeBaseViewModel = hiltViewModel()
    var coffees: List<Coffee> by remember { mutableStateOf(emptyList()) }

    val navDrawerViewModel: NavDrawerViewModel = hiltViewModel()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: Screens.MyCoffeeBase

    val sortMenuExpanded = remember { mutableStateOf(false) }
    val filterMenuExpanded = remember { mutableStateOf(false) }

    val searchModeActive = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }

    coffees = when (val uiState = coffeeBaseViewModel.myCoffeeBaseUiState) {
        is MyCoffeeBaseUiState.Success -> uiState.coffees
        else -> emptyList()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LaunchedEffect(true) {
            coffeeBaseViewModel.fetchInitData()
        }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        MyCoffeeBaseNavigationDrawer(
            currentRoute = currentRoute.toString(),
            navController = navController,
            scope = scope,
            drawerState = drawerState,
            viewModel = navDrawerViewModel
        ) {
            Scaffold(
                topBar = {
                    CoffeeBaseTopAppBar(
                        titleResId = R.string.my_coffeebase,
                        canShowNavigationDrawerIcon = true,
                        showNavigationDrawer = {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                        canNavigateBack = false,
                        navigateUp = { },
                        actions = {
                            SearchAction(
                                searchModeActive = searchModeActive,
                                searchQuery = searchQuery,
                                viewModel = coffeeBaseViewModel
                            )
                            SortAction(
                                showSortMenu = sortMenuExpanded,
                                viewModel = coffeeBaseViewModel
                            )
                            FilterAction(
//                                searchModeActive = searchModeActive,
                                showFilterMenu = filterMenuExpanded,
                                viewModel = coffeeBaseViewModel
                            )
                        }
                    )
                },
                content = { innerPadding ->
                    CoffeesGrid(
                        coffees = coffees,
                        navController = navController,
                        modifier = Modifier,
                        innerPadding
                    )
                },
            )
        }
    }
}

@Composable
fun CoffeesGrid(
    coffees: List<Coffee>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 10.dp),
        contentPadding = contentPadding
    ) {
        items(
            items = coffees,
            key = { coffee -> coffee.id!! }
        ) { coffee ->
            CoffeeCard(
                coffee,
                navController = navController,
                modifier = modifier
                    .padding(6.dp)
                    .wrapContentSize()
            )
        }
    }
}

@Composable
fun CoffeeCard(
    coffee: Coffee,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable{
          navController.navigate("${Screens.Coffee.name}/${coffee.id}")
        },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 170.dp)
                    .clip(RoundedCornerShape(45.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(coffee.getImageDownloadUrl())
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.coffee_photo),
                    placeholder = painterResource(R.drawable.coffeebean),
                    error = painterResource(R.drawable.coffeebean),
                    contentScale = ContentScale.Crop,
                    modifier = modifier.padding(vertical = 2.dp, horizontal = 6.dp)
                )
                if (coffee.favourite) {
                    Image(
                        painter = painterResource(R.drawable.ic_favorite_filled),
                        contentDescription = stringResource(R.string.favourite_icon),
                        modifier = modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 4.dp)
                    )
                }
            }
            Text(
                text = coffee.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(bottom = 5.dp, end = 5.dp, start = 5.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MyCoffeeBaseScreenPreview() {
//    CoffeeBaseTheme {
//        Scaffold(
//            topBar = {
//                CoffeeBaseTopAppBar(
//                    titleResId = R.string.my_coffeebase,
//                    canShowNavigationDrawerIcon = true,
//                    canNavigateBack = false,
//                    navigateUp = {},
//                    actions = {}
//                )
//            },
//            content = { padding ->
//                CoffeesGrid(
//                    coffees = listOf(
////                        Coffee(1, "coffee1", true, "", emptyList()),
////                        Coffee(2, "coffee2", false, "", emptyList()),
////                        Coffee(3, "coffee3", true, "", emptyList()),
////                        Coffee(4, "coffee4", false, "", emptyList()),
////                        Coffee(5, "coffee5", true, "", emptyList())
//                    ), modifier = Modifier, padding
//                )
//            }
//        )
//    }
//}
