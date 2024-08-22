package com.salman.trycar_test.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.salman.trycar_test.presentation.core.InternetConnectivityManager
import com.salman.trycar_test.presentation.core.LocalConnectivityManager
import com.salman.trycar_test.presentation.navigation.AppNavigation
import com.salman.trycar_test.presentation.navigation.LocalNavigator
import com.salman.trycar_test.presentation.navigation.TopNavigationAppBar
import com.salman.trycar_test.presentation.theme.TrycarTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrycarTestTheme {
                val navController = rememberNavController()
                val connectivityManager = InternetConnectivityManager(this)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopNavigationAppBar(navController = navController, connectivityManager)
                        }
                    ) {
                        CompositionLocalProvider(
                            LocalNavigator provides navController,
                            LocalConnectivityManager provides connectivityManager
                        ) {
                            AppNavigation(
                                navController = navController,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = it.calculateTopPadding())
                            )
                        }
                    }
                }
            }
        }
    }
}
