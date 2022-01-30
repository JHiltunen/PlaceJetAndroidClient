package com.jhiltunen.placejetandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jhiltunen.placejetandroid.ui.components.InsertProductForm
import com.jhiltunen.placejetandroid.viewmodels.ProductViewModel

class MainActivity : ComponentActivity() {
    companion object {
        private lateinit var productViewModel: ProductViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel = ProductViewModel(application)
        setContent {
            MainAppNav(productViewModel)
        }
    }
}

@Composable
fun ListProducts(
    productViewModel: ProductViewModel, navController: NavController
) {

    val productList = productViewModel.getAll().observeAsState(listOf())
    LazyColumn {
        item {
            Row {
                Text(stringResource(R.string.header))

            }

        }
        items(productList.value) {
            Text("Product: $it", Modifier.clickable {
                navController.navigate("details/${it.productId}")

            })

        }

    }

}

@Composable
 fun MainAppNav(productViewModel: ProductViewModel) {

    val navController = rememberNavController()
     NavHost (navController, startDestination = "main") {
         composable ("main") {
             Column {
                 InsertProductForm(productViewModel)
                 ListProducts (productViewModel, navController)

            }

        }
         composable ("details/{productId}") {

            val id = it.arguments?.getString("productId")?.toLong() ?: 0
             DetailView (productViewModel, id, navController)

        }

    }
}

@Composable
fun DetailView(productViewModel: ProductViewModel, id: Long, navController: NavController) {
    Text(text = "$id")
}
