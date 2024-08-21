package com.salman.trycar_test.presentation.screen.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salman.trycar_test.domain.usecase.GetFavoritePostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritePostsUseCase: GetFavoritePostsUseCase,
): ViewModel() {

    private val mutableState = MutableStateFlow(FavoritesUiState())
    val state = mutableState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() = viewModelScope.launch {
        getFavoritePostsUseCase().onEach {
            mutableState.value = state.value.copy(
                favorites = it,
                isLoading = false
            )
        }.launchIn(viewModelScope)
    }
}