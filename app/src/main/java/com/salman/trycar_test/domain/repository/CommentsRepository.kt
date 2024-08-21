package com.salman.trycar_test.domain.repository

import com.salman.trycar_test.domain.model.CommentItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 *
 * Separating Comments from [PostRepository] for future scalability
 * of Comments related features
 */
interface CommentsRepository {

    /**
     * @return flow of [CommentItem] list to provide comments as soon as they are available
     * from any data source
     */
    suspend fun getPostComments(postId: Int): Flow<List<CommentItem>>
}