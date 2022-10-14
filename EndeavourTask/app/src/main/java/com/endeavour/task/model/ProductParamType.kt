package com.endeavour.task.model

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

class ProductParamType : NavType<Product>(isNullableAllowed = false) {

        override fun get(bundle: Bundle, key: String): Product? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(key, Product::class.java)

            } else {
                @Suppress("DEPRECATION")
                bundle.getSerializable(key) as Product?
            }
        }

        override fun parseValue(value: String): Product {
            return Gson().fromJson(value, Product::class.java)
        }

        override fun put(bundle: Bundle, key: String, value: Product) {
            bundle.putSerializable(key, value)
        }
}