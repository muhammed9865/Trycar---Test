package com.salman.trycar_test.domain.usecase

import com.salman.trycar_test.domain.model.PostDetails
import com.salman.trycar_test.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
class GetPostDetailsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(postId: Int): Flow<PostDetails> {
        return withContext(Dispatchers.IO) {
            postRepository.getPostDetails(postId)
        }
    }
}