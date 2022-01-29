package com.jhiltunen.placejetandroid.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jhiltunen.placejetandroid.database.ProductDB
import com.jhiltunen.placejetandroid.entity.Product
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) :
    AndroidViewModel(application) {
    private val productDB = ProductDB.get(application)
    fun getAll(): LiveData<List<Product>> =
        productDB.productDao().getAll()

    fun insert(product: Product) {
        viewModelScope.launch {
            productDB.productDao().insert(product)
        }
    }

    /* fun update, delete, getDetails,... */
}