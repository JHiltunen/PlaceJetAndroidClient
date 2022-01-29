package com.jhiltunen.placejetandroid.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jhiltunen.placejetandroid.entity.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product): Long

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)
}