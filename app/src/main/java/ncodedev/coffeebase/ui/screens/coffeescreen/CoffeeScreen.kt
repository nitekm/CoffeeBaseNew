package ncodedev.coffeebase.ui.screens.coffeescreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ncodedev.coffeebase.R
import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.model.enums.Continent
import ncodedev.coffeebase.model.enums.RoastProfile
import ncodedev.coffeebase.ui.components.Screens
import ncodedev.coffeebase.ui.components.common.DisplayCoffeeInfoCard
import ncodedev.coffeebase.ui.components.common.LabelledText
import ncodedev.coffeebase.ui.components.common.RatingBar
import ncodedev.coffeebase.ui.components.topbar.CoffeeBaseTopAppBar
import ncodedev.coffeebase.ui.components.topbar.DeleteCoffeeAction
import ncodedev.coffeebase.ui.components.topbar.EditCoffeeAction
import ncodedev.coffeebase.ui.components.topbar.SwitchFavouriteAction
import ncodedev.coffeebase.ui.screens.editcoffee.tags.DisplayTag
import ncodedev.coffeebase.ui.theme.CoffeeBaseTheme

@Composable
fun CoffeeScreen(navController: NavHostController, coffeeId: Long) {

    var coffee by remember { mutableStateOf<Coffee?>(null) }
    val tabRowScrollState = rememberScrollState()

    val coffeeViewModel: CoffeeViewModel = hiltViewModel()
    coffee = null
    when (val uiState = coffeeViewModel.coffeeUiState) {
        is CoffeeUiState.Success -> coffee = uiState.coffee
        is CoffeeUiState.Empty -> navController.navigate(Screens.MyCoffeeBase.name)
        else -> Unit
    }

    LaunchedEffect(coffeeId) {
        if (coffeeId != 0L) {
            coffeeViewModel.getCoffee(coffeeId)
        }
    }
    Scaffold(
        topBar = {
            CoffeeBaseTopAppBar(
                titleResId = R.string.coffee,
                canNavigateBack = true,
                navigateUp = { navController.navigate(Screens.MyCoffeeBase.name) },
                actions = {
                    SwitchFavouriteAction(onClick = { coffee?.id?.let { coffeeViewModel.switchFavourite(it) } }, coffee)
                    EditCoffeeAction(onClick = { coffee?.id?.let { navController.navigate("${Screens.EditCoffee.name}/$it") } })
                    DeleteCoffeeAction(onClick = { coffee?.id?.let { coffeeViewModel.deleteCoffee(it) } })
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(coffee?.getImageDownloadUrl())
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.coffee_photo),
                    placeholder = painterResource(R.drawable.coffeebean),
                    error = painterResource(R.drawable.coffeebean),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 6.dp)
                )
                Text(
                    text = coffee?.name ?: "coffee name",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
                RatingBar(currentRating = coffee?.rating?.toInt() ?: 0)
                Row(modifier = Modifier.horizontalScroll(tabRowScrollState)) {
                    coffee?.tags?.forEach { tag ->
                        DisplayTag(
                            tagName = tag.name,
                            color = Integer.parseInt(tag.color)
                        )
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(top = 7.dp))
                DisplayCoffeeInfoCard(
                    title = stringResource(R.string.roast),
                    shouldDisplayCard = coffee?.roaster != null ||
                            coffee?.roastProfile != RoastProfile.ROAST_PROFILE.roastProfileValue
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        coffee?.roaster?.let {
                            LabelledText(
                                label = stringResource(R.string.roaster),
                                text = it
                            )
                        }
                        if (coffee?.roastProfile != RoastProfile.ROAST_PROFILE.roastProfileValue) {
                            LabelledText(
                                label = stringResource(R.string.roast_profile),
                                text = coffee?.roastProfile
                            )
                        }
                    }
                }
                DisplayCoffeeInfoCard(
                    title = stringResource(R.string.origin),
                    shouldDisplayCard = coffee?.continent != Continent.CONTINENT.continentValue ||
                            coffee?.region != null ||
                            coffee?.origin != null ||
                            coffee?.farm != null
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column {
                            if (coffee?.continent != Continent.CONTINENT.continentValue) {
                                LabelledText(
                                    label = stringResource(R.string.continent),
                                    text = coffee?.continent
                                )
                                Spacer(modifier = Modifier.padding(5.dp))
                            }
                            coffee?.region?.let {
                                LabelledText(
                                    label = stringResource(R.string.region),
                                    text = it
                                )
                            }
                        }
                        Column {
                            coffee?.origin?.let {
                                LabelledText(
                                    label = stringResource(R.string.origin),
                                    text = it
                                )
                                Spacer(modifier = Modifier.padding(5.dp))
                            }
                            coffee?.farm?.let {
                                LabelledText(
                                    label = stringResource(R.string.farm),
                                    text = it
                                )
                            }
                        }
                    }
                }
                DisplayCoffeeInfoCard(
                    title = stringResource(R.string.other),
                    shouldDisplayCard = coffee?.processing != null ||
                            coffee?.cropHeight != null ||
                            coffee?.scaRating != null
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        coffee?.processing?.let {
                            LabelledText(
                                label = stringResource(R.string.processing),
                                text = it
                            )
                        }
                        coffee?.cropHeight?.let {
                            LabelledText(
                                label = stringResource(R.string.crop_height),
                                text = it.toString()
                            )
                        }
                        coffee?.scaRating?.let {
                            LabelledText(
                                label = stringResource(R.string.sca_score),
                                text = it.toString()
                            )
                        }
                    }
                }
            }
        })
}

@Preview(showBackground = true)
@Composable
fun PreviewCoffeeScreen() {
    CoffeeBaseTheme {
        CoffeeScreen(
            navController = rememberNavController(),
            1L
        )
    }
}