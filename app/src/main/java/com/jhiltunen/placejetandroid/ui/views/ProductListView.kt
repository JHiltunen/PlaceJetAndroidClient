package com.jhiltunen.placejetandroid.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.jhiltunen.placejetandroid.R
import com.jhiltunen.placejetandroid.entity.Products
import com.jhiltunen.placejetandroid.ui.components.ProductForm
import com.jhiltunen.placejetandroid.viewmodels.ProductViewModel

@ExperimentalFoundationApi
@Composable
fun ProductListView(productViewModel: ProductViewModel, navController: NavController) {
    val productList = productViewModel.getAll().observeAsState(listOf())
    val openDialog = remember { mutableStateOf(false) }
    var product = remember { mutableStateOf(Products()) }

    LazyColumn {
        item {
            Row {
                Text(stringResource(R.string.header))
            }
        }
        items(productList.value) {
            Text("Products: $it", Modifier.combinedClickable(onClick = {
                navController.navigate("details/${it.productId}")
            }, onLongClick = {
                openDialog.value = true
                product.value = it
            }))
        }
    }
    if (openDialog.value) {
        AlertDialogSample(product.value, productViewModel, openDialog)
    }
}

@Composable
fun AlertDialogSample(product: Products, productViewModel: ProductViewModel, openDialogValue: MutableState<Boolean>) {
    if (openDialogValue.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialogValue.value = false
            },
            title = {
                Text(text = "${product.productId} - ${product.name}")
            },
            text = {
                ProductForm(productViewModel = productViewModel, product)
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    openDialogValue.value = false
                }) {
                    Text(stringResource(id = R.string.cancel))
                }
            },
            confirmButton = {
                Button(onClick = {
                    productViewModel.update(product)
                    openDialogValue.value = false
                }) {
                    Text(stringResource(id = R.string.update))
                }
            }
        )
    }
}