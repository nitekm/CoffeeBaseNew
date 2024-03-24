package ncodedev.coffeebase.ui.screens.editcoffee

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ncodedev.coffeebase.R
import ncodedev.coffeebase.ui.components.Screens
import ncodedev.coffeebase.ui.components.topbar.CoffeeBaseTopAppBar
import ncodedev.coffeebase.ui.screens.editcoffee.coffeeimage.CoffeeImageFromGallery
import ncodedev.coffeebase.ui.screens.editcoffee.coffeeimage.CoffeeImageViewModel
import ncodedev.coffeebase.ui.screens.editcoffee.tabs.GeneralCoffeeInfo
import ncodedev.coffeebase.ui.screens.editcoffee.tabs.OriginCoffeeInfo
import ncodedev.coffeebase.ui.screens.editcoffee.tabs.OtherCoffeeInfo
import ncodedev.coffeebase.ui.screens.editcoffee.tags.AddTagDialog
import ncodedev.coffeebase.ui.screens.editcoffee.tags.DisplayTag
import ncodedev.coffeebase.ui.theme.CoffeeBaseTheme

@Composable
fun EditCoffeeScreen(navController: NavHostController, coffeeId: Long) {
    val context = LocalContext.current
    val editCoffeeViewModel: EditCoffeeViewModel = hiltViewModel()
    val coffeeImageViewModel = viewModel<CoffeeImageViewModel>()

    val tabItems = listOf(
        stringResource(R.string.general),
        stringResource(R.string.origin),
        stringResource(R.string.other)
    )
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabRowScrollState = rememberScrollState()
    val showAddTagDialog = remember { mutableStateOf(false) }

    when (editCoffeeViewModel.editCoffeeUiState) {
        is EditCoffeeUiState.Success -> navController.navigate(Screens.MyCoffeeBase.name)
        else -> Unit
    }

    LaunchedEffect(coffeeId) {
        coffeeId.let { id ->
            if (id != 0L) {
                editCoffeeViewModel.getCoffeeToEdit(coffeeId)
            }
        }
    }
    Scaffold(
        topBar = {
            CoffeeBaseTopAppBar(
                titleResId = R.string.edit_coffee,
                canNavigateBack = true,
                navigateUp = { navController.navigate(Screens.MyCoffeeBase.name) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoffeeImageFromGallery(
                    modifier = Modifier.padding(top = 15.dp),
                    coffeeImageViewModel = coffeeImageViewModel
                )
                Text(
                    text = stringResource(R.string.tap_to_change_image),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Button(
                    onClick = { showAddTagDialog.value = true },
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = stringResource(R.string.add_tag),
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 5.dp)
                    )
                    Text(text = stringResource(R.string.add_tag), fontSize = 13.sp)
                }
                AddTagDialog(showAddTagDialog = showAddTagDialog, editCoffeeViewModel)
                Row(modifier = Modifier.horizontalScroll(tabRowScrollState)) {
                    editCoffeeViewModel.tags.forEach { tag ->
                        DisplayTag(
                            tagName = tag.name,
                            color = Integer.parseInt(tag.color)
                        )
                    }
                }
                HorizontalDivider()
                TabRow(
                    selectedTabIndex = tabIndex,
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    tabItems.forEachIndexed { index, title ->
                        Tab(
                            selected = tabIndex == index,
                            onClick = { tabIndex = index },
                            selectedContentColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedContentColor = MaterialTheme.colorScheme.tertiary
                        ) {
                            Text(text = title)
                        }
                    }
                }
                Box {
                    when (tabIndex) {
                        0 -> GeneralCoffeeInfo(editCoffeeViewModel)
                        1 -> OriginCoffeeInfo(editCoffeeViewModel)
                        2 -> OtherCoffeeInfo(editCoffeeViewModel)
                    }
                }
            }
            Button(
                //wywołac saveAction to bedzie zmienne zaleznie czy edit czy new coffee
                onClick = { editCoffeeViewModel.saveCoffee(context, coffeeImageViewModel.imageBitMap.value) },
                enabled = editCoffeeViewModel.isNameValid.value,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 30.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 15.dp,
                    pressedElevation = 20.dp
                )
            ) {
                Text(
                    text = stringResource(R.string.save),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 40.dp, vertical = 5.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditCoffeeScreenPreview() {
    CoffeeBaseTheme {
        EditCoffeeScreen(navController = rememberNavController(), 0L)
    }
}
