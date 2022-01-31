package com.jhiltunen.placejetandroid.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jhiltunen.placejetandroid.database.ProductDB
import com.jhiltunen.placejetandroid.entity.Locations
import kotlinx.coroutines.launch

class LocationsViewModel(application: Application) :
    AndroidViewModel(application) {
    private val productDB = ProductDB.get(application)
    fun getAll(): LiveData<List<Locations>> =
        productDB.locationsDao().getAll()

    fun insert(location: Locations) {
        viewModelScope.launch {
            productDB.locationsDao().insert(location)
        }
    }

    /* fun update, delete, getDetails,... */
}