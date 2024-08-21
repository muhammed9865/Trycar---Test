package com.salman.trycar_test.presentation.navigation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 *
 * Access to the navigation controller from anywhere in the app
 */
val LocalNavigator: ProvidableCompositionLocal<NavController> = staticCompositionLocalOf {
    throw IllegalStateException("Navigator not initialized")
}