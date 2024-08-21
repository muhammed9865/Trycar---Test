package com.salman.trycar_test.presentation.screen.details

import com.salman.trycar_test.domain.model.CommentItem

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
data class DetailsUiState(
    val postTitle: String = "",
    val postBody: String = "",
    val comments: List<CommentItem> = emptyList(),
    val isLoadingComments: Boolean = true,
    val isFavorite: Boolean = false
)
