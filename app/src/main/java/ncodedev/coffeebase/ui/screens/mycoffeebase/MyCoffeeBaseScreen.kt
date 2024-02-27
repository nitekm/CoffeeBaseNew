package ncodedev.coffeebase.ui.screens.mycoffeebase

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
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
import ncodedev.coffeebase.model.FilterOptions
import ncodedev.coffeebase.model.SortOptions
import ncodedev.coffeebase.ui.components.CoffeeBaseTopAppBar
import ncodedev.coffeebase.ui.components.Screens
import ncodedev.coffeebase.ui.components.navdrawer.MyCoffeeBaseNavigationDrawer
import ncodedev.coffeebase.ui.components.navdrawer.NavDrawerViewModel
import java.util.*

@Composable
fun MyCoffeeBaseScreen(navController: NavHostController) {

    val coffeeBaseViewModel: MyCoffeeBaseViewModel = hiltViewModel()
    var coffees: List<Coffee> by remember { mutableStateOf(emptyList()) }

    val navDrawerViewModel: NavDrawerViewModel = hiltViewModel()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: Screens.MyCoffeeBase

    var sortMenuExpanded by remember { mutableStateOf(false) }
    var showDialog = remember { mutableStateOf(false) }
    val appliedFilters =  mutableStateMapOf<FilterOptions, Boolean>()

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
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = stringResource(R.string.search)
                                )
                            }
                            IconButton(
                                onClick = { sortMenuExpanded = true }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.sort_filled_24),
                                    contentDescription = stringResource(R.string.sort)
                                )
                            }
                            DropdownMenu(expanded = sortMenuExpanded, onDismissRequest = { sortMenuExpanded = false }) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(R.string.sort),
                                            color = Color.Gray,
                                            fontSize = 17.sp
                                        )
                                    },
                                    onClick = { }
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
                                    onClick = { }
                                )
                                SortOptions.entries.forEach { sortOption ->
                                    SortMenuItem(
                                        sortOptions = sortOption,
                                        onSortOptionsSelected = { coffeeBaseViewModel.fetchSorted(sortOption) }
                                    )
                                }
                            }
                            IconButton(onClick = { showDialog.value = true }) {
                                Icon(
                                    painter = painterResource(R.drawable.filter_filled_24),
                                    contentDescription = stringResource(R.string.do_filter)
                                )
                            }
                            FilterMenu(showDialog, appliedFilters, applyFilter = { coffeeBaseViewModel.fetchFiltered(appliedFilters.value) })
                        }
                    )
                },
                content = { innerPadding ->
                    CoffeesGrid(
                        coffees = coffees,
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
            key = { coffee -> coffee.id }
        ) { coffee ->
            CoffeeCard(
                coffee,
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
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
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
                        .data(coffee.imageUrl)
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

@Composable
fun SortMenuItem(sortOptions: SortOptions, onSortOptionsSelected: () -> Unit) {
    DropdownMenuItem(
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(sortOptions.contentDescitpionResId),
                modifier = Modifier.rotate(sortOptions.iconRotateValue)
            )
        },
        text = { Text(text = stringResource(sortOptions.nameResId)) },
        onClick = onSortOptionsSelected
    )
}

@Composable
fun FilterMenu(showFilterMenu: MutableState<Boolean>, currentFilters: MutableMap<FilterOptions, Boolean>, applyFilter: (Map<String, List<FilterOptions>>) -> Unit) {

    val filtersByGroup = FilterOptions.entries.groupBy { it.filterKey }

    filtersByGroup.keys.forEach { filters ->
        filtersByGroup[filters]?.forEach {
            currentFilters[it] = currentFilters[it] ?: false
        }
    }
    DropdownMenu(
        expanded = showFilterMenu.value,
        onDismissRequest = { showFilterMenu.value = false },
    ) {
        filtersByGroup.keys.forEach { key ->
            Text(
                text = key.replaceFirstChar { it.titlecase(Locale.getDefault()) },
                modifier = Modifier.padding(7.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            val filterGroup = filtersByGroup[key]
            filterGroup?.forEach { filterOption ->
                var checkedState by remember { mutableStateOf(false) }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            currentFilters[filterOption] = !(currentFilters[filterOption] ?: false)
                            checkedState = !checkedState
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = currentFilters[filterOption] ?: false,
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
                applyFilter(filtersByGroup)
                showFilterMenu.value = false
            },
            colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.tertiaryContainer)
        )
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
//                    actions = {
//                        IconButton(onClick = {}) {
//                            Icon(
//                                imageVector = Icons.Filled.Search,
//                                contentDescription = stringResource(R.string.search)
//                            )
//                        }
//                    }
//                )
//            },
//            content = { padding ->
//                CoffeesGrid(
//                    coffees = listOf(
//                        Coffee(
//                            1,
//                            "coffee1",
//                            true,
//                            ""
//                        )
//                    ), modifier = Modifier, padding
//                )
//            }
//        )
//    }
//}
