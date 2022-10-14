package com.endeavour.task.feature.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.endeavour.task.network.ApiRepository
import com.endeavour.task.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
) : FavouritesViewModel(apiRepository) {

    private val _state = mutableStateOf<Result>(Result.Loading)
    val state: State<Result> = _state

    fun getProducts() =
        viewModelScope.launch {
            if (_state.value !is Result.Success<*>) {
                apiRepository.products().collect {
                    _state.value = it
                }
            }
        }
}