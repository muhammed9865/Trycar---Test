package com.salman.trycar_test.presentation.screen.posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.salman.trycar_test.R
import com.salman.trycar_test.domain.model.PostItem
import com.salman.trycar_test.presentation.composable.LoadingIndicator
import com.salman.trycar_test.presentation.composable.PostsList
import com.salman.trycar_test.presentation.composable.Screen
import com.salman.trycar_test.presentation.navigation.LocalNavigator
import com.salman.trycar_test.presentation.navigation.Routes

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@Composable
fun PostsScreen(
    viewModel: PostsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navigator = LocalNavigator.current
    Screen {
        PostsContent(
            state = state,
        ) {
            navigator.navigate(Routes.DETAILS.createRoute(it.id))
        }
    }
}

@Composable
private fun PostsContent(
    state: PostsUiState,
    onPostClicked: (PostItem) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            LoadingIndicator(text = stringResource(id = R.string.loading_posts))
        } else {
            PostsList(posts = state.posts, emptyListMessage = stringResource(id = R.string.no_posts), onPostClicked = onPostClicked)
        }
    }
}

