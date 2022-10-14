package com.endeavour.task

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.endeavour.task.feature.view.FavouriteScreen
import com.endeavour.task.feature.view.DetailsScreen
import com.endeavour.task.feature.view.ProductScreen
import com.endeavour.task.model.Product
import com.endeavour.task.model.ProductParamType
import com.endeavour.task.ui.theme.EndeavourTaskTheme
import com.endeavour.task.feature.view.tab.BottomNavigation
import com.endeavour.task.feature.view.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LoadView() }

    }
}

@Composable
fun LoadView() {
    EndeavourTaskTheme {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = { BottomNavigation(navController) }
        ) {
            Box(modifier = Modifier.padding(it)) {
                NavigationGraph(navController)
            }
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, Screen.ProductsScreen.route) {

        composable(route = Screen.ProductsScreen.route) {
            ProductScreen(navController = navController)
        }

        composable(route = Screen.FavouriteScreen.route) {
            FavouriteScreen(navController = navController)
        }
        composable(route = Screen.DetailsScreen.route+ "/{product}",
            arguments = listOf(
                navArgument("product") {
                    type = ProductParamType()
                }
            )) {

            val product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.arguments?.getSerializable("product", Product::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.arguments?.getSerializable("product") as Product?
            }

            DetailsScreen(product)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EndeavourTaskTheme {
        LoadView()
    }
}