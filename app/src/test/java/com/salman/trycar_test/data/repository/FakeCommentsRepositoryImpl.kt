package com.salman.trycar_test.data.repository

import com.salman.trycar_test.data.util.FakeModel
import com.salman.trycar_test.domain.model.CommentItem
import com.salman.trycar_test.domain.repository.CommentsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCommentsRepositoryImpl : CommentsRepository {
    override suspend fun getPostComments(postId: Int): Flow<List<CommentItem>> {
        return flow {
            emit(FakeModel.fakeCommentItems(startId = 1)) // cached
            delay(100)
            emit(FakeModel.fakeCommentItems(startId = 5)) // remote
        }
    }
}