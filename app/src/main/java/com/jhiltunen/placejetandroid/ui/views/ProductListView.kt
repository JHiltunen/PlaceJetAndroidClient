package com.jhiltunen.placejetandroid.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jhiltunen.placejetandroid.R
import com.jhiltunen.placejetandroid.entity.Products
import com.jhiltunen.placejetandroid.ui.components.ProductItem
import com.jhiltunen.placejetandroid.ui.components.UpdateProductDialog
import com.jhiltunen.placejetandroid.viewmodels.ProductViewModel

@ExperimentalFoundationApi
@Composable
fun ProductListView(productViewModel: ProductViewModel, navController: NavController) {
    val productList = productViewModel.getAll().observeAsState(listOf())

    LazyColumn {
        item {
            Row {
                Text(stringResource(R.string.header))
            }
        }
        items(productList.value) {
            ProductItem(navController, product = it, productViewModel = productViewModel)
        }
    }
}