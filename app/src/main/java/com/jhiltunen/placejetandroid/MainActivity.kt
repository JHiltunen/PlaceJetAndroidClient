package com.jhiltunen.placejetandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jhiltunen.placejetandroid.entity.CableType
import com.jhiltunen.placejetandroid.entity.Product
import com.jhiltunen.placejetandroid.entity.ProductType
import com.jhiltunen.placejetandroid.ui.components.ProductTypeDropDown
import com.jhiltunen.placejetandroid.viewmodels.ProductViewModel

class MainActivity : ComponentActivity() {
    companion object {
        private lateinit var productViewModel: ProductViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ProductViewModel(application)
        setContent {
            MainAppNav(productViewModel)
        }
    }
}

@Composable
fun InsertProduct(productViewModel: ProductViewModel) {
    // candiate for mutableStateListOf
    var productName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var productType by remember { mutableStateOf("")}
    var cableType by remember { mutableStateOf("") }
    var cableLength by remember { mutableStateOf(0.0) }
    var manufacturer by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var imageSrc by remember { mutableStateOf("") }

    Column {
        TextField(value = productName, label = { Text(text = stringResource(id = R.string.productName))}, onValueChange = { productName = it })
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            ProductTypeDropDown()
        }
        Button(onClick = {
            productViewModel.insert(Product(productId = 0, name = productName, description = description, productType = ProductType.valueOf(productType), cableType = CableType.valueOf(cableType) , cableLength = cableLength, manufacturer = manufacturer, model = model, imageSrc = imageSrc))
        }) {
            Text(stringResource(R.string.insert))
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
            Text("Product: $it", Modifier.clickable {
                navController.navigate("details/${it.productId}")

            })

        }

    }

}

@Composable
 fun MainAppNav(productViewModel: ProductViewModel) {

    val navController = rememberNavController()
     NavHost (navController, startDestination = "main") {
         composable ("main") {
             Column {
                 InsertProduct (productViewModel)
                 ListProducts (productViewModel, navController)

            }

        }
         composable ("details/{productId}") {

            val id = it.arguments?.getString("productId")?.toLong() ?: 0
             DetailView (productViewModel, id, navController)

        }

    }
}

@Composable
fun DetailView(productViewModel: ProductViewModel, id: Long, navController: NavController) {
    Text(text = "$id")
}
