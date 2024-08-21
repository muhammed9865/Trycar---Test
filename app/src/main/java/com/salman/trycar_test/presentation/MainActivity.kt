package com.salman.trycar_test.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopNavigationAppBar(navController = navController)
                        }
                    ) {
                        CompositionLocalProvider(value = LocalNavigator provides navController) {
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

@Composable
private fun Content(tab: Int) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Tab $tab")
    }
}
