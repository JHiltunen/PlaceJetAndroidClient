package com.jhiltunen.placejetandroid.ui.navigator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jhiltunen.placejetandroid.ui.components.InsertProductForm
import com.jhiltunen.placejetandroid.ui.views.DetailView
import com.jhiltunen.placejetandroid.ui.views.ProductListView
import com.jhiltunen.placejetandroid.viewmodels.LocationsViewModel
import com.jhiltunen.placejetandroid.viewmodels.ProductViewModel

@ExperimentalFoundationApi
@Composable
fun MainAppNav(productViewModel: ProductViewModel, locationsViewModel: LocationsViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {
        composable("main") {
            Column {
                InsertProductForm(productViewModel)
                ProductListView(productViewModel, navController)
            }

        }
        composable("details/{productId}") {

            val id = it.arguments?.getString("productId")?.toLong() ?: 0
            DetailView(productViewModel, locationsViewModel = locationsViewModel, id)

        }
    }
}