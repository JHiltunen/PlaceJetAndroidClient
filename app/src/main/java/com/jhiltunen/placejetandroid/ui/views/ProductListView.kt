package com.jhiltunen.placejetandroid.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.jhiltunen.placejetandroid.R
import com.jhiltunen.placejetandroid.viewmodels.ProductViewModel

@Composable
fun ProductListView(
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