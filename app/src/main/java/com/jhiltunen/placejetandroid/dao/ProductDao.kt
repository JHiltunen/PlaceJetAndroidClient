package com.jhiltunen.placejetandroid.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jhiltunen.placejetandroid.entity.ProductLocations
import com.jhiltunen.placejetandroid.entity.Products

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAll(): LiveData<List<Products>>

    @Query("SELECT * FROM products WHERE products.productId = :productId")
    // the @Relation do the INNER JOIN for you ;)
    fun getProductsWithLocations(productId: Long): LiveData<ProductLocations>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(products: Products): Long

    @Update
    suspend fun update(products: Products)

    @Delete
    suspend fun delete(products: Products)
}