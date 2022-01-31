package com.jhiltunen.placejetandroid.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.jhiltunen.placejetandroid.R
import com.jhiltunen.placejetandroid.ui.components.CustomRadioGroup
import com.jhiltunen.placejetandroid.viewmodels.LocationsViewModel
import com.jhiltunen.placejetandroid.viewmodels.ProductViewModel

@ExperimentalFoundationApi
@Composable
fun DetailView(productViewModel: ProductViewModel, locationsViewModel: LocationsViewModel, id: Long) {
    var product = productViewModel.getDetails(id).observeAsState()
    var productLocations = productViewModel.getProductWithLocation(id).observeAsState()
    var locations = productLocations.value?.locations
    Column {
        Text(text = "${product.value?.productId}")
        Text(text = "${product.value?.name}")
        Text(text = "${product.value?.description}")
        Text(text = "${product.value?.productType}")
        Text(text = "${product.value?.cableType}")
        Text(text = "${product.value?.cableLength}")
        Text(text = "${product.value?.manufacturer}")
        Text(text = "${product.value?.model}")
        Text(text = "${product.value?.imageSrc}")
        product.value?.productId?.let { CustomRadioGroup(locationsViewModel, it, locations) }
        if (!locations.isNullOrEmpty()) {
            Text(text = stringResource(id = R.string.location,
                locations.first().location?.displayName ?: "No location selected"
            )
            )
        }
    }
}