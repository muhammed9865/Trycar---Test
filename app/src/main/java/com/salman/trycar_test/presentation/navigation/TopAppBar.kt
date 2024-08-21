package com.salman.trycar_test.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.salman.trycar_test.R

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationAppBar(
    navController: NavController,
) {
    val currentRoute by navController.currentBackStackEntryAsState()
    val (title, showNavigationIcon) = when (currentRoute?.destination?.route) {
        Routes.DETAILS.route -> {
            stringResource(R.string.post) to true
        }

        else -> {
            stringResource(R.string.hello_there) to false
        }
    }

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = if (showNavigationIcon) {
            {
                IconButton(onClick = navController::popBackStack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        } else {
            {}
        }
    )

}