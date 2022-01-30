package com.jhiltunen.placejetandroid.entity

import androidx.room.Embedded
import androidx.room.Relation

class ProductLocations {
    @Embedded
    var product: Products? = null
    @Relation(parentColumn = "productId", entityColumn = "productId")
    var locations: List<Locations>? = null
}