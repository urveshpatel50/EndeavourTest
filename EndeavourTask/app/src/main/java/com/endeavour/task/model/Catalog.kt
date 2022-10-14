package com.endeavour.task.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Catalog(val products: ArrayList<Product> = ArrayList())


@Entity
data class Product(
    @PrimaryKey val id: String,
    @ColumnInfo val title: String?,
    @ColumnInfo val imageURL: String?,
    @ColumnInfo val ratingCount: Float?,
    @ColumnInfo val isAddToCartEnable: Boolean?,
    @ColumnInfo val addToCartButtonText: String?,
    @ColumnInfo val price: ArrayList<Price>?,
    @ColumnInfo var isFavourite: Boolean = false,
    ) : Serializable

data class Price(
    val message: String?,
    val value: Float?,
    val isOfferPrice: Boolean = false
) : Serializable