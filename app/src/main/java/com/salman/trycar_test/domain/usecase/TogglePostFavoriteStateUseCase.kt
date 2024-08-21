package com.salman.trycar_test.domain.usecase

import com.salman.trycar_test.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
class TogglePostFavoriteStateUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {

    suspend operator fun invoke(postId: Int) = withContext(Dispatchers.IO) {
        postRepository.toggleFavorite(postId)
    }
}