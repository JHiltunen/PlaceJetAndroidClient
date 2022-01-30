package com.jhiltunen.placejetandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jhiltunen.placejetandroid.entity.*
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
    var productsExpanded by remember { mutableStateOf(false) }
    var cablesExpanded by remember { mutableStateOf(false) }
    var showAnother by remember { mutableStateOf(false) }
    val productTypes = enumValues<ProductType>().map { it.displayName }
    val cableTypes = enumValues<CableType>().map { it.displayName }
    var productName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedProductType by remember { mutableStateOf("") }
    var selectedCableType by remember { mutableStateOf("") }
    var cableLength by remember { mutableStateOf("") }
    var manufacturer by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var imageSrc by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (productsExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.ArrowDropDown

    Column {
        TextField(value = productName, label = { Text(text = stringResource(id = R.string.product_name))}, onValueChange = { productName = it })
        TextField(value = description, label = { Text(text = stringResource(id = R.string.product_description))}, onValueChange = { description = it })
        TextField(value = manufacturer, label = { Text(text = stringResource(id = R.string.product_manufacturer))}, onValueChange = { manufacturer = it })
        TextField(value = model, label = { Text(text = stringResource(id = R.string.product_model))}, onValueChange = { model = it })

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Column {
                OutlinedTextField(
                    value = selectedProductType,
                    onValueChange = { selectedProductType = it },

                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            //This value is used to assign to the DropDown the same width
                            textfieldSize = coordinates.size.toSize()
                        }
                        .clickable {
                            productsExpanded = !productsExpanded
                        },
                    enabled = false,
                    label = { Text("Product type") },
                    trailingIcon = {
                        Icon(icon,"contentDescription",
                            Modifier.clickable { productsExpanded = !productsExpanded })
                    }
                )
                DropdownMenu(
                    expanded = productsExpanded,
                    onDismissRequest = { productsExpanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current){textfieldSize.width.toDp()})
                ) {
                    productTypes.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedProductType = label
                            productsExpanded = false

                            if (selectedProductType == ProductType.AUDIOCABLE.displayName || selectedProductType == ProductType.DISPLAYCABLE.displayName || selectedProductType == ProductType.POWERCABLE.displayName) {
                                showAnother = true
                            } else {
                                selectedCableType = ""
                                showAnother = false
                            }
                        }) {
                            Text(text = label)
                        }
                    }
                }

                if (showAnother) {
                    OutlinedTextField(
                        value = selectedCableType,
                        onValueChange = { selectedCableType = it },

                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                //This value is used to assign to the DropDown the same width
                                textfieldSize = coordinates.size.toSize()
                            }
                            .clickable {
                                cablesExpanded = !cablesExpanded
                            },
                        enabled = false,
                        label = { Text("Cable type") },
                        trailingIcon = {
                            Icon(icon,"contentDescription",
                                Modifier.clickable { cablesExpanded = !cablesExpanded })
                        }
                    )
                    DropdownMenu(
                        expanded = cablesExpanded,
                        onDismissRequest = { cablesExpanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                            .clickable { cablesExpanded = !cablesExpanded }
                    ) {
                        cableTypes.forEach { label ->
                            DropdownMenuItem(onClick = {
                                selectedCableType = label
                                cablesExpanded = false
                            }) {
                                Text(text = label)
                            }
                        }
                    }
                    TextField(value = cableLength, label = { Text(text = stringResource(id = R.string.cable_length))}, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), onValueChange = { cableLength = it })

                }
            }
        }

        Button(onClick = {
            productViewModel.insert(Product(productId = 0, name = productName, description = description, productType = getValueFromProductTypes(s = selectedProductType), cableType = getValueFromCableTypes(selectedCableType), cableLength = if (cableLength.isEmpty()) null else {cableLength.toDouble()}, manufacturer = manufacturer, model = model, imageSrc = imageSrc))
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
