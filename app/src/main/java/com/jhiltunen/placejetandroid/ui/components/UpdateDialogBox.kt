package com.jhiltunen.placejetandroid.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.jhiltunen.placejetandroid.R
import com.jhiltunen.placejetandroid.entity.Products
import com.jhiltunen.placejetandroid.viewmodels.ProductViewModel

@Composable
fun UpdateProductDialog(product: Products, productViewModel: ProductViewModel, openDialogValue: MutableState<Boolean>) {
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