package com.jhiltunen.placejetandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jhiltunen.placejetandroid.dao.ProductDao
import com.jhiltunen.placejetandroid.entity.Product

@Database(entities = [(Product::class)], version = 1)
abstract class ProductDB : RoomDatabase() {
    abstract fun productDao(): ProductDao

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