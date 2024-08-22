package com.salman.trycar_test.presentation.screen.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salman.trycar_test.domain.usecase.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
) : ViewModel() {

    private val mutableState = MutableStateFlow(PostsUiState())
    val state = mutableState.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() = viewModelScope.launch {
        mutableState.update { it.copy(isLoading = true) }
        getPostsUseCase().collect { posts ->
            mutableState.update { it.copy(posts = posts, isLoading = false) }
        }
    }


}