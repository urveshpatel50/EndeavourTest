package com.endeavour.task.feature.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endeavour.task.model.Product
import com.endeavour.task.network.ApiRepository
import com.endeavour.task.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class FavouritesViewModel @Inject constructor(private val apiRepository: ApiRepository) :
    ViewModel() {
    private val _favState = mutableStateOf<Result>(Result.Success(null))
    val favState: State<Result> = _favState

    fun favouritesProductList() {
        viewModelScope.launch {
            apiRepository.fetchFavourites().collect {
                _favState.value = it
            }
        }
    }

    fun deleteFavourites(product: Product,
                         onError: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                apiRepository.deleteFavourites(product)
                favouritesProductList()
            } catch (e: Exception) {
                onError(Exception("Please try again later."))
            }
        }
    }

    fun insertFavourites(vararg products: Product,
                         onError: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                apiRepository.insertFavourites(*products)
                favouritesProductList()
            } catch (e: Exception) {
                onError(Exception("Please try again later."))
            }
        }
    }

    fun existInFavouriteList(product: Product): Boolean {
        var exist = false

        val result = _favState.value
        if (result is Result.Success<*>) {
            val products = (result.data as List<*>?)?.filterIsInstance<Product>()
                .takeIf { it?.size == result.data?.size }
            exist = products?.any { it.id == product.id } ?: false
        }
        return exist
    }
}