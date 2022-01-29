package com.jhiltunen.placejetandroid.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import com.jhiltunen.placejetandroid.entity.CableType
import com.jhiltunen.placejetandroid.entity.ProductType

@Composable
fun ProductTypeDropDown() {
    var productsExpanded by remember { mutableStateOf(false) }
    var cablesExpanded by remember { mutableStateOf(false) }
    var showAnother by remember { mutableStateOf(false) }
    var productTypes = enumValues<ProductType>().map { it.displayName }
    var cableTypes = enumValues<CableType>().map { it.displayName }
    var selectedProductType by remember { mutableStateOf("") }
    var selectedCableType by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (productsExpanded)
        Icons.Filled.KeyboardArrowUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown


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
                    .width(with(LocalDensity.current){textfieldSize.width.toDp()})
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
        }
    }
}