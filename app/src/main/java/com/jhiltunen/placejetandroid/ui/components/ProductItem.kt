package com.jhiltunen.placejetandroid.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jhiltunen.placejetandroid.entity.Products
import com.jhiltunen.placejetandroid.viewmodels.ProductViewModel

@ExperimentalFoundationApi
@Composable
fun ProductItem(
    navController: NavController,
    productViewModel: ProductViewModel,
    product: Products
) {
    val openDialog = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .combinedClickable(onClick = {
                navController.navigate("details/${product.productId}")
            }, onLongClick = {
                openDialog.value = true
            }),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(product.name, style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(8.dp))
            Text(product.description, style = MaterialTheme.typography.body1)
            Spacer(Modifier.height(8.dp))
            Text(
                "${product.productType?.displayName} > ${product.cableType?.displayName}",
                style = MaterialTheme.typography.caption
            )
        }
    }
    if (openDialog.value) {
        UpdateProductDialog(product, productViewModel, openDialog)
    }
}