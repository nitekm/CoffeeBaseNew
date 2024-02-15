package ncodedev.coffeebase.ui.screens.mycoffeebase

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ncodedev.coffeebase.R
import ncodedev.coffeebase.model.Coffee
import ncodedev.coffeebase.ui.theme.CoffeeBaseTheme

@Composable
fun MyCoffeeBaseScreen() {
    val coffees = listOf(
        Coffee(1, "coffee1", "https://neurosciencenews.com/files/2023/06/coffee-brain-caffeine-neuroscincces.jpg"),
        Coffee(
            2,
            "coffee2",
            "https://img.freepik.com/darmowe-zdjecie/pary-swiezej-kawy-na-drewnianym-stole-zamykaja-generatywna-ai_188544-8923.jpg"
        ),
        Coffee(3, "coffee3", "https://www.rush.edu/sites/default/files/media-images/Coffee_OpenGraph.png")
    )
    CoffeesGrid(coffees = coffees, modifier = Modifier)
}

@Composable
fun CoffeesGrid(
    coffees: List<Coffee>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 10.dp),
        contentPadding = contentPadding
    ) {
        items(items = coffees, key = { coffee -> coffee.id }) { coffee ->
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
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(coffee.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.coffeePhoto),
                placeholder = painterResource(R.drawable.coffeebean),
                error = painterResource(R.drawable.coffeebean),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(width = 150.dp, height = 130.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .padding(all = 5.dp)
            )
            Text(
                text = coffee.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .align(Alignment.CenterHorizontally)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyCoffeeBaseScreenPreview() {
    CoffeeBaseTheme {
        MyCoffeeBaseScreen()
    }
}