package com.salman.trycar_test.presentation.screen.posts

import com.salman.trycar_test.domain.model.PostItem

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
data class PostsUiState(
    val posts: List<PostItem> = emptyList(),
    val isLoading: Boolean = true,
)
