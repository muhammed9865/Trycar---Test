package com.salman.trycar_test.presentation.core

import androidx.compose.runtime.staticCompositionLocalOf

val LocalConnectivityManager = staticCompositionLocalOf<InternetConnectivityManager> {
    error("No ConnectivityManager provided")
}