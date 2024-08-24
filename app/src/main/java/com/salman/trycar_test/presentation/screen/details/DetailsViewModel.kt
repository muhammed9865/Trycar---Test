package com.salman.trycar_test.presentation.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salman.trycar_test.domain.model.CommentItem
import com.salman.trycar_test.domain.model.PostDetails
import com.salman.trycar_test.domain.usecase.GetPostCommentsUseCase
import com.salman.trycar_test.domain.usecase.GetPostDetailsUseCase
import com.salman.trycar_test.domain.usecase.TogglePostFavoriteStateUseCase
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
    private val togglePostFavoriteStateUseCase: TogglePostFavoriteStateUseCase,
): ViewModel() {

    private val postId = checkNotNull(savedStateHandle.get<Int>("postId"))
    private val mutableState = MutableStateFlow(DetailsUiState())
    val state = mutableState.asStateFlow()

    init {
        loadPostDetails(postId)
    }

    fun toggleFavoriteState() {
       viewModelScope.launch {
           togglePostFavoriteStateUseCase(postId)
       }
    }

    private fun loadPostDetails(postId: Int) = viewModelScope.launch {
        launch {
            getPostDetailsUseCase(postId).collect(::updatePostState)
        }
        launch {
            getPostCommentsUseCase(postId).collect(::updatePostCommentsState)
        }
    }

    private fun updatePostState(postDetails: PostDetails) {
        mutableState.value = state.value.copy(
            postTitle = postDetails.post.title,
            postBody = postDetails.post.body,
            isFavorite = postDetails.isFavorite
        )
    }

    private fun updatePostCommentsState(commentsResult: Result<List<CommentItem>>) {
        commentsResult.fold(
            onSuccess = { comments ->
                mutableState.value = state.value.copy(
                    comments = comments,
                    isLoadingComments = false
                )
            },
            onFailure = {
                mutableState.value = state.value.copy(
                    isLoadingComments = false,
                )
            }
        )

    }
}