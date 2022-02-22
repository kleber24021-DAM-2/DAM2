package com.quevedo.footballvideos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.quevedo.footballvideos.ui.screens.individual.IndividualScreen
import com.quevedo.footballvideos.ui.screens.main.MainScreen
import com.quevedo.footballvideos.ui.screens.main.MainViewmodel


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: MainViewmodel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = NavItem.MainNavItem.route
    ) {
        composable(NavItem.MainNavItem) {
            MainScreen(
                onNavigate = {
                    navController.navigate(NavItem.DetailNavItem.route)
                },
                viewModel
            )
        }
        composable(NavItem.DetailNavItem) {
            IndividualScreen(viewModel)
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