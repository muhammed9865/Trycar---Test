package com.salman.trycar_test.presentation.screen.favorites

import com.salman.trycar_test.domain.model.PostItem

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
data class FavoritesUiState(
    val favorites: List<PostItem> = emptyList(),
    val isLoading: Boolean = true
)
