package com.salman.trycar_test.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.salman.trycar_test.presentation.screen.details.DetailsScreen
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
        startDestination = Routes.HOME.route,
        enterTransition = {
            val towards = if (initialState.destination.route == Routes.HOME.route)
                AnimatedContentTransitionScope.SlideDirection.Start
            else
                AnimatedContentTransitionScope.SlideDirection.End

            slideIntoContainer(
                animationSpec = tween(300),
                towards = towards
            )
        },
        exitTransition = {
            val towards = if (targetState.destination.route == Routes.HOME.route)
                AnimatedContentTransitionScope.SlideDirection.End
            else
                AnimatedContentTransitionScope.SlideDirection.Start
            slideOutOfContainer(
                animationSpec = tween(300),
                towards = towards
            )
        },
    ) {
        composable(Routes.HOME.route) {
            HomeScreen()
        }

        composable(
            Routes.DETAILS.route,
            arguments = Routes.DETAILS.navArguments()
        ) {
            DetailsScreen()
        }
    }
}