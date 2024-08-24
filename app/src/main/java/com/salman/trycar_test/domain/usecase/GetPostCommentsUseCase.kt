package com.salman.trycar_test.domain.usecase

import com.salman.trycar_test.domain.model.CommentItem
import com.salman.trycar_test.domain.repository.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
class GetPostCommentsUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository
) {
    suspend operator fun invoke(postId: Int): Flow<Result<List<CommentItem>>> {
        return withContext(Dispatchers.IO) {
            commentsRepository.getPostComments(postId)
        }
    }
}