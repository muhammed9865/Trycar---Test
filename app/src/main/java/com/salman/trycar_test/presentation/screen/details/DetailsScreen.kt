package com.salman.trycar_test.presentation.screen.details

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.salman.trycar_test.R
import com.salman.trycar_test.domain.model.CommentItem
import com.salman.trycar_test.presentation.composable.CommentUIItem
import com.salman.trycar_test.presentation.composable.LoadingIndicator
import com.salman.trycar_test.presentation.composable.Screen
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
    comments: List<CommentItem>
) {
    if (isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LoadingIndicator(
                text = stringResource(R.string.loading_comments),
            )
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimen.contentPadding.dp),
    ) {
        items(comments, key = { it.id }) { comment ->
            CommentUIItem(comment = comment, modifier = Modifier.padding(bottom = spaceExtra.dp))
        }
    }
}

private fun Modifier.dynamicHeight(lazyListState: LazyListState): Modifier = this.layout { measurable, constraints ->
    // Calculate scroll percentage
    val scrollOffset = lazyListState.firstVisibleItemScrollOffset
    val totalHeight = constraints.maxHeight
    val dynamicHeight = if (scrollOffset > 0) {
        // Reduce the height based on scroll percentage
        totalHeight - (scrollOffset * 0.5f).toInt().coerceAtLeast(0)
    } else {
        totalHeight
    }

    // Measure the composable with the dynamic height
    val placeable = measurable.measure(constraints.copy(maxHeight = dynamicHeight))
    layout(constraints.maxWidth, dynamicHeight) {
        placeable.placeRelative(0, 0)
    }
}