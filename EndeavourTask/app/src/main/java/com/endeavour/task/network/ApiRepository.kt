package com.endeavour.task.network

import com.endeavour.task.database.ProductDao
import com.endeavour.task.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    private val service: APIService,
    private val productDao: ProductDao
) {
    fun products(): Flow<Result> = flow {
        try {
            emit(Result.Loading)
            val catalog = service.getProducts()
            emit(Result.Success(catalog))
        } catch (e: Exception) {
            //handle rest exceptions here.
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    fun fetchFavourites(): Flow<Result> = flow {
        try {
            emit(Result.Loading)
            val favouriteList = productDao.findFavoritesList()
            emit(Result.Success(favouriteList))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    @Throws(Exception::class)
    suspend fun deleteFavourites(product: Product) {
        try {
            withContext(Dispatchers.IO) {
                productDao.delete(product)
            }
        } catch (exception: Exception) {
            throw exception
        }
    }

    @Throws(Exception::class)
    suspend fun insertFavourites(vararg products: Product) {
        try {
            withContext(Dispatchers.IO) {
                productDao.insert(*products)
            }
        } catch (exception: Exception) {
            throw exception
        }
    }
}