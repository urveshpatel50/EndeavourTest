package com.endeavour.task.feature.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.endeavour.task.feature.view.component.HandleResult
import com.endeavour.task.feature.view.component.ShowProductList
import com.endeavour.task.model.Catalog
import com.endeavour.task.feature.viewmodel.ProductViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun ProductScreen(navController: NavController?) {
    val viewModel = hiltViewModel<ProductViewModel>()

    fun load() {
        viewModel.getProducts()
        viewModel.favouritesProductList()
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    load()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        HandleResult(result = viewModel.state.value) { result ->
            val products = (result.data as Catalog?)?.products

            ShowProductList(products = products) {
                it.isFavourite = viewModel.existInFavouriteList(it)

                val json = Uri.encode(Gson().toJson(it))
                ListItem(it, {
                    navController?.navigate(Screen.DetailsScreen.withArgs(json))
                }) {
                    if (it.isFavourite) {
                        viewModel.deleteFavourites(it) { e ->
                            scope.launch {
                                snackbarHostState.showSnackbar(e.message.toString())
                            }
                        }
                    } else {
                        it.isFavourite = true
                        viewModel.insertFavourites(it) { e ->
                            scope.launch {
                                snackbarHostState.showSnackbar(e.message.toString())
                            }
                        }
                    }
                }

                SnackbarHost(hostState = snackbarHostState)
            }
        }
    }
}