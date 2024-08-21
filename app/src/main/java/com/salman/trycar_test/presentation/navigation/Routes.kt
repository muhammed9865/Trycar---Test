package com.salman.trycar_test.presentation.navigation

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.salman.trycar_test.R

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
sealed class Routes(
    val route: String
) {
    data object HOME : Routes("home") {
        val tabs by lazy {
            listOf(
                Tab(
                    title = R.string.posts,
                    selectedIcon = Icons.Rounded.Email,
                    unselectedIcon = Icons.Outlined.Email,
                ),
                Tab(
                    title = R.string.favorites,
                    selectedIcon = Icons.Rounded.Favorite,
                    unselectedIcon = Icons.Rounded.FavoriteBorder,
                ),
            )
        }


        data class Tab(
            @StringRes val title: Int,
            val selectedIcon: ImageVector,
            val unselectedIcon: ImageVector,
        ) {
            fun icon(isSelected: Boolean) = if (isSelected) selectedIcon else unselectedIcon
        }
    }

    data object DETAILS : Routes("details/{postId}") {
        fun createRoute(postId: Int): String {
            return "details/$postId"
        }
        fun navArguments(): List<NamedNavArgument> {
            return listOf(
                navArgument("postId") { type = NavType.IntType }
            )
        }

        fun getArgs(arguments: Bundle): DetailsArgs {
            return DetailsArgs.fromArguments(arguments)
        }

        data class DetailsArgs(
            val postId: Int
        ) {
            companion object {
                fun fromArguments(arguments: Bundle): DetailsArgs {
                    val postId = arguments.getInt("postId")
                    return DetailsArgs(postId)
                }
            }
        }
    }
}