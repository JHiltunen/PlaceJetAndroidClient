package com.jhiltunen.placejetandroid.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jhiltunen.placejetandroid.entity.Locations
import com.jhiltunen.placejetandroid.entity.ProductLocations

@Dao
interface LocationsDao {
    @Query("SELECT * FROM locations")
    fun getAll(): LiveData<List<Locations>>

    //@Query("SELECT * FROM locations WHERE locations.product = :productId")
    // the @Relation do the INNER JOIN for you ;)
    //fun getProductsWithLocations(productId: Long): LiveData<ProductLocations>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locations: Locations): Long

    @Update
    suspend fun update(locations: Locations)

    @Delete
    suspend fun delete(locations: Locations)
}