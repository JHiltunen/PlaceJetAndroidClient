package com.jhiltunen.placejetandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jhiltunen.placejetandroid.dao.LocationsDao
import com.jhiltunen.placejetandroid.dao.ProductDao
import com.jhiltunen.placejetandroid.entity.Products
import com.jhiltunen.placejetandroid.entity.Locations

@Database(entities = [(Products::class), (Locations::class)], version = 1)
abstract class ProductDB : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun locationsDao(): LocationsDao

    companion object {
        private var sInstance: ProductDB? = null

        @Synchronized
        fun get(context: Context): ProductDB {
            if (sInstance == null) {
                sInstance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ProductDB::class.java, "products.db"
                    ).build()
            }
            return sInstance!!
        }
    }
}