package com.endeavour.task.feature.view.tab
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.endeavour.task.ui.theme.LightNeutral
import com.endeavour.task.ui.theme.LightPrimary
import com.endeavour.task.ui.theme.MediumPrimary
import com.endeavour.task.feature.view.Screen

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        Screen.ProductsScreen,
        Screen.FavouriteScreen
    )
    BottomNavigation(
        backgroundColor = MediumPrimary,
        contentColor = LightPrimary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { item.icon?.let { Icon(it, contentDescription = item.title) } },
                label = {
                    item.title?.let {
                        Text(text = it,
                            fontSize = 10.sp)
                    }
                },
                selectedContentColor = LightNeutral,
                unselectedContentColor = LightPrimary,
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }

}
