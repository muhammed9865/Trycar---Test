package com.salman.trycar_test.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.salman.trycar_test.presentation.screen.home.HomeScreen

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.HOME.route
    ) {
        composable(Routes.HOME.route) {
            HomeScreen()
        }

        composable(
            Routes.DETAILS.route,
            arguments = Routes.DETAILS.navArguments()
        ) {
            if (it.arguments == null) return@composable
            val args = Routes.DETAILS.getArgs(it.arguments!!)
            // TODO : Show Details Screen
        }
    }
}