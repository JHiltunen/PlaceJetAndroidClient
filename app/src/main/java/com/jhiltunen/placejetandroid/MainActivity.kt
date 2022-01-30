package com.jhiltunen.placejetandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jhiltunen.placejetandroid.entity.Location
import com.jhiltunen.placejetandroid.entity.Locations
import com.jhiltunen.placejetandroid.entity.getValueFromLocationTypes
import com.jhiltunen.placejetandroid.ui.components.InsertProductForm
import com.jhiltunen.placejetandroid.viewmodels.LocationsViewModel
import com.jhiltunen.placejetandroid.viewmodels.ProductViewModel

class MainActivity : ComponentActivity() {
    companion object {
        private lateinit var productViewModel: ProductViewModel
        private lateinit var locationsViewModel: LocationsViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ProductViewModel(application)
        locationsViewModel = LocationsViewModel(application)
        setContent {
            MainAppNav(productViewModel, locationsViewModel)
        }
    }
}

@Composable
fun ListProducts(
    productViewModel: ProductViewModel, navController: NavController
) {

    val productList = productViewModel.getAll().observeAsState(listOf())
    LazyColumn {
        item {
            Row {
                Text(stringResource(R.string.header))

            }

        }
        items(productList.value) {
            Text("Products: $it", Modifier.clickable {
                navController.navigate("details/${it.productId}")

            })

        }

    }

}

@Composable
 fun MainAppNav(productViewModel: ProductViewModel, locationsViewModel: LocationsViewModel) {

    val navController = rememberNavController()
     NavHost (navController, startDestination = "main") {
         composable ("main") {
             Column {
                 InsertProductForm(productViewModel)
                 ListProducts (productViewModel, navController)

            }

        }
         composable ("details/{productId}") {

            val id = it.arguments?.getString("productId")?.toLong() ?: 0
             DetailView (productViewModel, locationsViewModel = locationsViewModel, id, navController)

        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailView(productViewModel: ProductViewModel, locationsViewModel: LocationsViewModel, id: Long, navController: NavController) {
    var product = productViewModel.getDetails(id).observeAsState()
    Column {
        Text(text = "${product.value?.productId}")
        Text(text = "${product.value?.name}")
        Text(text = "${product.value?.description}")
        Text(text = "${product.value?.productType}")
        Text(text = "${product.value?.cableType}")
        Text(text = "${product.value?.cableLength}")
        Text(text = "${product.value?.manufacturer}")
        Text(text = "${product.value?.model}")
        Text(text = "${product.value?.imageSrc}")
        product.value?.productId?.let { CustomRadioGroup(locationsViewModel, it) }
    }
}

@ExperimentalFoundationApi
@Composable
fun CustomRadioGroup(locationsViewModel: LocationsViewModel, productId: Long) {
    val options = enumValues<Location>().map { it.displayName }
    var selectedOption by remember {
        mutableStateOf(options.first())
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }
    Text(text = "Select product location:")
    LazyVerticalGrid(
        cells = GridCells.Adaptive(150.dp),

        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(options.size) { index ->
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .clip(
                            shape = RoundedCornerShape(
                                size = 12.dp,
                            ),
                        ),
                    elevation = 8.dp,
                ) {
                    Text(
                        text = options[index],
                        style = typography.body1.merge(),
                        color = Color.White,
                        modifier = Modifier
                            .clip(
                                shape = RoundedCornerShape(
                                    size = 12.dp,
                                ),
                            )
                            .clickable {
                                onSelectionChange(options[index])
                            }
                            .background(
                                if (options[index] == selectedOption) {
                                    Color.Magenta
                                } else {
                                    Color.LightGray
                                }
                            )
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp,
                            ),
                    )
                }
            }
        }
    )

    Button(onClick = {
        locationsViewModel.insert(Locations(locationId = 0, productId = productId, location = getValueFromLocationTypes(selectedOption)))
    }) {
        Text(text = "Save location")
    }

    /*Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        options.forEach { text ->
            Row(
                modifier = Modifier
                    .padding(
                        all = 8.dp,
                    ),
            ) {
                Text(
                    text = text,
                    style = typography.body1.merge(),
                    color = Color.White,
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                size = 12.dp,
                            ),
                        )
                        .clickable {
                            onSelectionChange(text)
                        }
                        .background(
                            if (text == selectedOption) {
                                Color.Magenta
                            } else {
                                Color.LightGray
                            }
                        )
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp,
                        ),
                )
            }
        }
    }*/
}
