package com.salman.trycar_test.data.source.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
class PostsRemoteSource @Inject constructor(
    private val postsService: PostsApiService,
) {

    suspend fun getPosts() = runCatching { postsService.getPosts() }

    suspend fun getPostComments(postId: Int) = runCatching { postsService.getPostComments(postId) }

    suspend fun toggleFavorite(postId: Int, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        delay(1000)
    }
}