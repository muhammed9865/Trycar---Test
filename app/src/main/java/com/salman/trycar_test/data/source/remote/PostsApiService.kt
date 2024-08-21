package com.salman.trycar_test.data.source.remote

import com.salman.trycar_test.data.source.remote.dto.CommentDTO
import com.salman.trycar_test.data.source.remote.dto.PostDTO
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
interface PostsApiService {

    @GET("posts")
    suspend fun getPosts(): List<PostDTO>

    @GET("posts/{postId}/comments")
    suspend fun getPostComments(
        @Path("postId") postId: Int
    ): List<CommentDTO>
}