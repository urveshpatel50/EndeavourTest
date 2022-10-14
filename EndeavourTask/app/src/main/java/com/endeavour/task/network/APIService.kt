package com.endeavour.task.network

import com.endeavour.task.model.Catalog
import retrofit2.http.GET

interface APIService {

    @GET("v3/2f06b453-8375-43cf-861a-06e95a951328")
    suspend fun getProducts() : Catalog
}