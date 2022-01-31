package com.jhiltunen.placejetandroid.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.jhiltunen.placejetandroid.R
import com.jhiltunen.placejetandroid.ui.components.ProductItem
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