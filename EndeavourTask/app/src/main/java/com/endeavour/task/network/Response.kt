package com.endeavour.task.network

open class Result {
    object Loading : Result()
    data class Success<out Data>(val data: Data?) : Result()
    data class Failure(val error: Exception) : Result()
}
