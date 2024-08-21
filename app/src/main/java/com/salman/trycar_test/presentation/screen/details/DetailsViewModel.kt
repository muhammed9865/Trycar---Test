package com.salman.trycar_test.presentation.screen.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salman.trycar_test.domain.model.CommentItem
import com.salman.trycar_test.domain.model.PostDetails
import com.salman.trycar_test.domain.usecase.GetPostCommentsUseCase
import com.salman.trycar_test.domain.usecase.GetPostDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostDetailsUseCase: GetPostDetailsUseCase,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
): ViewModel() {

    private val postId = checkNotNull(savedStateHandle.get<Int>("postId"))
    private val mutableState = MutableStateFlow(DetailsUiState())
    val state = mutableState.asStateFlow()
    private var isFavorite = false

    init {
        loadPostDetails(postId)
    }

    fun toggleFavoriteState() {
       viewModelScope.launch {
           getPostDetailsUseCase(postId, !isFavorite).also {
               isFavorite = !isFavorite
               updatePostState(it)
           }
       }
    }

    private fun loadPostDetails(postId: Int) = viewModelScope.launch {
        launch {
            getPostDetailsUseCase(postId, isFavorite).also(::updatePostState)
        }
        launch {
            getPostCommentsUseCase(postId).also(::updatePostCommentsState)
        }
    }

    private fun updatePostState(postDetails: PostDetails) {
        mutableState.value = state.value.copy(
            postTitle = postDetails.post.title,
            postBody = postDetails.post.body,
            isFavorite = postDetails.isFavorite
        )
    }

    private fun updatePostCommentsState(comments: List<CommentItem>) {
        mutableState.value = state.value.copy(
            comments = comments,
            isLoadingComments = false
        )
    }
}