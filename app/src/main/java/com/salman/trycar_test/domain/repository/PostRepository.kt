package com.salman.trycar_test.domain.repository

import com.salman.trycar_test.domain.model.PostDetails
import com.salman.trycar_test.domain.model.PostItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
interface PostRepository {

    /**
     * @return flow of [PostItem] to provide posts as soon as they are available
     * from any data source
     */
    suspend fun getPosts(): Flow<Result<List<PostItem>>>

    /**
     * @return flow of [PostItem] to reflect new added favorite posts
     */
    suspend fun getFavoritePosts(): Flow<List<PostItem>>

    /**
     * @return flow of [PostDetails] to reflect any changes in the post details
     */
    suspend fun getPostDetails(postId: Int): Flow<PostDetails>

    suspend fun toggleFavorite(postId: Int)
}