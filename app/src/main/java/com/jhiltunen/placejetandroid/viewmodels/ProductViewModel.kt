package com.jhiltunen.placejetandroid.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jhiltunen.placejetandroid.database.ProductDB
import com.jhiltunen.placejetandroid.entity.Products
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) :
    AndroidViewModel(application) {
    private val productDB = ProductDB.get(application)
    fun getAll(): LiveData<List<Products>> =
        productDB.productDao().getAll()

    fun getDetails(productId: Long): LiveData<Products> = productDB.productDao().getProductDetails(productId = productId)

    fun insert(products: Products) {
        viewModelScope.launch {
            productDB.productDao().insert(products)
        }
    }

    /* fun update, delete, getDetails,... */
}