package com.salman.trycar_test.data.repository

import com.salman.trycar_test.data.mapper.toDomain
import com.salman.trycar_test.data.mapper.toEntity
import com.salman.trycar_test.data.source.local.PostsLocalSource
import com.salman.trycar_test.data.source.remote.PostsRemoteSource
import com.salman.trycar_test.data.source.remote.dto.CommentDTO
import com.salman.trycar_test.domain.model.CommentItem
import com.salman.trycar_test.domain.repository.CommentsRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
class CommentsRepositoryImpl @Inject constructor(
    private val postsLocalSource: PostsLocalSource,
    private val postsRemoteSource: PostsRemoteSource,
): CommentsRepository {
    override suspend fun getPostComments(postId: Int): Flow<List<CommentItem>> {
        return coroutineScope {
            flow {
                val cachedComments = postsLocalSource.getPostWithComments(postId)
                // Emit cached comments first
                emit(cachedComments.comments.map { it.toDomain() })

                val remoteComments = postsRemoteSource.getPostComments(postId)
                remoteComments.onSuccess { comments ->
                    cacheRemoteComments(comments)
                    emit(comments.map { it.toDomain() })
                }

                // Indicate that the flow has completed
                currentCoroutineContext().cancel(null)
            }
        }
    }

    private suspend fun cacheRemoteComments(comments: List<CommentDTO>) {
        postsLocalSource.insertComments(comments.map { it.toEntity() })
    }
}