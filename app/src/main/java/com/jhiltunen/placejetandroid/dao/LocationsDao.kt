package com.jhiltunen.placejetandroid.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jhiltunen.placejetandroid.entity.Locations

@Dao
interface LocationsDao {
    @Query("SELECT * FROM locations")
    fun getAll(): LiveData<List<Locations>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locations: Locations): Long

    @Update
    suspend fun update(locations: Locations)

    @Delete
    suspend fun delete(locations: Locations)
}