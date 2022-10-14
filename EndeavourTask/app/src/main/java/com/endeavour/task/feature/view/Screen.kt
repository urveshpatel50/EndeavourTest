package com.endeavour.task.feature.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(var title:String? = null, var icon:ImageVector? = null, val route: String) {
    object ProductsScreen : Screen("Products", Icons.Filled.Home,"product_screen")
    object DetailsScreen: Screen(title = "Product Details", route ="details_screen")
    object FavouriteScreen: Screen("Favourite",Icons.Filled.Favorite,"favourite_screen")

    // Helper class to help pass simple arguments when navigating
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}