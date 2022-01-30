package com.jhiltunen.placejetandroid.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Products::class, onDelete = CASCADE, parentColumns = ["productId"], childColumns = ["productId"])])
data class Locations(
    @PrimaryKey
    val locationId: Long,
    val productId: Long,
    val location: Location?
)