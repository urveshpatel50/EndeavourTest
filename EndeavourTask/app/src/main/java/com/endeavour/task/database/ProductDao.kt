package com.endeavour.task.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.endeavour.task.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun findFavoritesList(): List<Product>?

    @Insert
    fun insert(vararg products: Product)

    @Delete
    fun delete(product: Product)
}
