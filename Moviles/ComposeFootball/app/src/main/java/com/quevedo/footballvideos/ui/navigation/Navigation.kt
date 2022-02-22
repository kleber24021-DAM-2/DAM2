package com.quevedo.footballvideos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.quevedo.footballvideos.ui.screens.main.MainScreen
import com.quevedo.footballvideos.ui.screens.main.MainViewmodel


@Composable
fun Navigation(
    viewModel: MainViewmodel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavItem.MainNavItem.route
    ) {
        composable(NavItem.MainNavItem) {
            MainScreen(
                onNavigate = {
                    navController.navigate(NavItem.DetailNavItem.route)
                },
                viewModel = viewModel
            )
        }
        composable(NavItem.DetailNavItem) {
            // TODO: Crear la ventana de detalles de video
        }
    }
}

private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navItem.route
    ) {
        content(it)
    }
}