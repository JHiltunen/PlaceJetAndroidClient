package com.jhiltunen.placejetandroid.ui.components

import com.jhiltunen.placejetandroid.R
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jhiltunen.placejetandroid.entity.Location
import com.jhiltunen.placejetandroid.entity.Locations
import com.jhiltunen.placejetandroid.entity.getValueFromLocationTypes
import com.jhiltunen.placejetandroid.viewmodels.LocationsViewModel

@ExperimentalFoundationApi
@Composable
fun CustomRadioGroup(locationsViewModel: LocationsViewModel, productId: Long, locations: List<Locations>?) {
    val options = enumValues<Location>().map { it.displayName }

    var selectedOption by remember {
        mutableStateOf(if (!locations.isNullOrEmpty()) locations.last().location?.displayName else "")
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }
    Text(text = stringResource(id = R.string.select_product_location))
    LazyVerticalGrid(
        cells = GridCells.Adaptive(150.dp),

        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(options.size) { index ->
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .clip(
                            shape = RoundedCornerShape(
                                size = 12.dp,
                            ),
                        ),
                    elevation = 8.dp,
                ) {
                    Text(
                        text = options[index],
                        style = MaterialTheme.typography.body1.merge(),
                        color = Color.White,
                        modifier = Modifier
                            .clip(
                                shape = RoundedCornerShape(
                                    size = 12.dp,
                                ),
                            )
                            .clickable {
                                onSelectionChange(options[index])
                            }
                            .background(
                                if (options[index] == selectedOption) {
                                    Color.Magenta
                                } else {
                                    Color.LightGray
                                }
                            )
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp,
                            ),
                    )
                }
            }
        }
    )

    Button(enabled = selectedOption?.isNotEmpty() ?: false, onClick = {
        locationsViewModel.insert(Locations(locationId = 0, productId = productId, location = getValueFromLocationTypes(selectedOption)))
    }) {
        Text(text = stringResource(id = R.string.save_location))
    }
}