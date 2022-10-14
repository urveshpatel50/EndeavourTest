package com.endeavour.task.feature.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.endeavour.task.feature.view.component.HandleResult
import com.endeavour.task.feature.view.component.ShowProductList
import com.endeavour.task.feature.viewmodel.FavouritesViewModel
import com.endeavour.task.model.Product
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun FavouriteScreen(navController: NavController?) {
    val viewModel = hiltViewModel<FavouritesViewModel>()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun load() {
        viewModel.favouritesProductList()
    }

    load()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

       HandleResult(viewModel.favState.value) { result ->
           val products = (result.data as List<*>?)?.filterIsInstance<Product>()
               .takeIf { it?.size == result.data?.size }


           ShowProductList(products) {
               val json = Uri.encode(Gson().toJson(it))
               ListItem(it, {
                   navController?.navigate(Screen.DetailsScreen.withArgs(json))
               }) {
                   viewModel.deleteFavourites(it) { e ->
                       scope.launch {
                           snackbarHostState.showSnackbar(e.message.toString())
                       }
                   }
               }
           }
       }
    }
}