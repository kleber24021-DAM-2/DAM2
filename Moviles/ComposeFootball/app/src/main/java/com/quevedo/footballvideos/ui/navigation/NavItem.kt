package com.quevedo.footballvideos.ui.navigation

import com.quevedo.footballvideos.ui.Routes

sealed class NavItem(
    private val baseRoute: String,
) {
    object MainNavItem : NavItem(Routes.MAIN)
    object DetailNavItem : NavItem(Routes.DETAIL)

    val route = run{
        baseRoute
    }
}