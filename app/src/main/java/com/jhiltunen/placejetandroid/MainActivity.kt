package com.jhiltunen.placejetandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jhiltunen.placejetandroid.ui.views.DetailView
import com.jhiltunen.placejetandroid.ui.views.ProductListView
import com.jhiltunen.placejetandroid.ui.components.InsertProductForm
import com.jhiltunen.placejetandroid.ui.navigator.MainAppNav
import com.jhiltunen.placejetandroid.viewmodels.LocationsViewModel
import com.jhiltunen.placejetandroid.viewmodels.ProductViewModel

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    companion object {
        private lateinit var productViewModel: ProductViewModel
        private lateinit var locationsViewModel: LocationsViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ProductViewModel(application)
        locationsViewModel = LocationsViewModel(application)
        setContent {
            MainAppNav(productViewModel, locationsViewModel)
        }
    }
}
