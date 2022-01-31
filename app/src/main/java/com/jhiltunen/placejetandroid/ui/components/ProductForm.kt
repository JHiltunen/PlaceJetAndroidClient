package com.jhiltunen.placejetandroid.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.toSize
import com.jhiltunen.placejetandroid.R
import com.jhiltunen.placejetandroid.entity.*
import com.jhiltunen.placejetandroid.viewmodels.ProductViewModel

@Composable
fun ProductForm(productViewModel: ProductViewModel, product: Products?) {
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
    val imageSrc by remember { mutableStateOf("") }
    if (product != null) {
        productName = product.name
        selectedProductType = product.productType?.displayName ?: ""
        selectedCableType = product.cableType?.displayName ?: ""
        if (selectedProductType == ProductType.AUDIOCABLE.displayName || selectedProductType == ProductType.DISPLAYCABLE.displayName || selectedProductType == ProductType.POWERCABLE.displayName) {
            showAnother = true
        }
        description = product.description
        cableLength = product.cableLength.toString()
        manufacturer = product.manufacturer.toString()
        model = product.model.toString()
    }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (productsExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.ArrowDropDown

    Column {
        TextField(
            value = productName,
            label = { Text(text = stringResource(id = R.string.product_name)) },
            onValueChange = {
                productName = it
                product?.name = it
            })
        TextField(
            value = description,
            label = { Text(text = stringResource(id = R.string.product_description)) },
            onValueChange = {
                description = it
                product?.description = it
            })
        TextField(
            value = manufacturer,
            label = { Text(text = stringResource(id = R.string.product_manufacturer)) },
            onValueChange = {
                manufacturer = it
                product?.manufacturer = it
            })
        TextField(
            value = model,
            label = { Text(text = stringResource(id = R.string.product_model)) },
            onValueChange = {
                model = it
                product?.model = it
            })

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Column {
                OutlinedTextField(
                    value = selectedProductType,
                    onValueChange = {
                        selectedProductType = it
                        product?.productType = ProductType.valueOf(it)
                    },

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
                    label = { Text("Products type") },
                    trailingIcon = {
                        Icon(icon, "contentDescription",
                            Modifier.clickable { productsExpanded = !productsExpanded })
                    }
                )
                DropdownMenu(
                    expanded = productsExpanded,
                    onDismissRequest = { productsExpanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                ) {
                    productTypes.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedProductType = label
                            product?.productType = getValueFromProductTypes(selectedProductType)
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
                        onValueChange = {
                            selectedCableType = it
                            selectedProductType = it
                            product?.cableType = getValueFromCableTypes(it)
                        },

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
                            Icon(icon, "contentDescription",
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
                    TextField(
                        value = cableLength,
                        label = { Text(text = stringResource(id = R.string.cable_length)) },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            cableLength = it
                            selectedProductType = it
                            product?.cableLength
                        })

                }
            }
        }

        if (product == null) {
            Button(onClick = {
                productViewModel.insert(
                    Products(
                        productId = 0,
                        name = productName,
                        description = description,
                        productType = getValueFromProductTypes(s = selectedProductType),
                        cableType = getValueFromCableTypes(selectedCableType),
                        cableLength = if (cableLength.isEmpty()) null else {
                            cableLength.toDouble()
                        },
                        manufacturer = manufacturer,
                        model = model,
                        imageSrc = imageSrc
                    )
                )
            }) {
                Text(stringResource(R.string.insert))
            }
        }
    }
}