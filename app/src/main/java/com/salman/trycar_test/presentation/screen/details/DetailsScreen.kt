package com.salman.trycar_test.presentation.screen.details

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.salman.trycar_test.R
import com.salman.trycar_test.domain.model.CommentItem
import com.salman.trycar_test.presentation.composable.CommentUIItem
import com.salman.trycar_test.presentation.composable.LoadingIndicator
import com.salman.trycar_test.presentation.composable.Screen
import com.salman.trycar_test.presentation.core.LocalConnectivityManager
import com.salman.trycar_test.presentation.theme.Dimen
import com.salman.trycar_test.presentation.theme.Dimen.spaceExtra
import com.salman.trycar_test.presentation.theme.Dimen.spaceMedium

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Screen {
        DetailsContent(state = state) {
            viewModel.toggleFavoriteState()
        }
    }
}

@Composable
private fun DetailsContent(
    state: DetailsUiState,
    onFavoriteClick: () -> Unit = {}
) {
    val connectivityManager = LocalConnectivityManager.current
    val isInternetConnected by connectivityManager.isConnected.collectAsStateWithLifecycle()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onFavoriteClick) {
                val imageVector = if (state.isFavorite) {
                    Icons.Rounded.Favorite
                } else {
                    Icons.Rounded.FavoriteBorder
                }
                Icon(imageVector = imageVector, contentDescription = "Favorite button")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            PostContent(
                title = state.postTitle, body = state.postBody,
                modifier = Modifier
                    .animateContentSize()
            )
            CommentsSection(
                isLoading = state.isLoadingComments,
                isInternetConnected = isInternetConnected,
                comments = state.comments,
            )
        }
    }
}

@Composable
private fun PostContent(
    modifier: Modifier = Modifier,
    title: String,
    body: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spaceMedium.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = Dimen.contentPadding.dp),
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            modifier = Modifier.padding(horizontal = Dimen.contentPadding.dp),
            text = body,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        HorizontalDivider()
    }
}

@Composable
private fun CommentsSection(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isInternetConnected: Boolean,
    comments: List<CommentItem>
) {
    if (isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LoadingIndicator(
                text = stringResource(R.string.loading_comments),
            )
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            contentPadding = PaddingValues(Dimen.contentPadding.dp),
        ) {
            items(comments, key = { it.id }) { comment ->
                CommentUIItem(
                    comment = comment,
                    modifier = Modifier.padding(bottom = spaceExtra.dp)
                )
            }


            item {
                LoadingCommentsIndicator(
                    isInternetConnected = isInternetConnected,
                    isLoading = isLoading,
                    isCommentListEmpty = comments.isEmpty()
                )
            }
        }
    }
}

@Composable
private fun LoadingCommentsIndicator(
    modifier: Modifier = Modifier,
    isInternetConnected: Boolean,
    isLoading: Boolean,
    isCommentListEmpty: Boolean,
) {
    val errorMessageStringId = when {
        isLoading -> null
        isCommentListEmpty && isInternetConnected.not() -> R.string.empty_comments_no_internet
        isCommentListEmpty && isInternetConnected -> R.string.post_have_no_comments
        else -> null
    }

    if (errorMessageStringId != null) {
        Text(
            text = stringResource(errorMessageStringId),
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    } else {
        if (isLoading)
            LoadingIndicator(
                text = stringResource(id = R.string.loading_comments),
                modifier = modifier.fillMaxWidth()
            )
    }

}