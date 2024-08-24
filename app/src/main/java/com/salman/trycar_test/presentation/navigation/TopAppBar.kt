package com.salman.trycar_test.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.salman.trycar_test.R
import com.salman.trycar_test.presentation.core.InternetConnectivityManager
import com.salman.trycar_test.presentation.theme.Dimen

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationAppBar(
    navController: NavController,
    connectivityManager: InternetConnectivityManager,
) {
    val currentRoute by navController.currentBackStackEntryAsState()
    val isInternetConnected by connectivityManager.isConnected.collectAsStateWithLifecycle()

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
        },
        actions = {
            if (!isInternetConnected) {
                NoInternetConnection()
            }
        }
    )
}

@Composable
private fun NoInternetConnection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimen.spaceSmall.dp),
        modifier = Modifier.padding(end = Dimen.spaceMedium.dp)
    ) {
        Text(
            text = stringResource(R.string.no_internet_connection),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
        Icon(
            imageVector = Icons.Default.Warning, contentDescription = "No internet connection",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(16.dp)
        )
    }
}