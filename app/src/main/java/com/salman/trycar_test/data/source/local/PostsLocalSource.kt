package com.salman.trycar_test.data.source.local

import com.salman.trycar_test.data.source.local.database.CacheDatabase
import com.salman.trycar_test.data.source.local.entity.CommentEntity
import com.salman.trycar_test.data.source.local.entity.PostEntity
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
class PostsLocalSource @Inject constructor(
    private val cacheDatabase: CacheDatabase,
) {

    fun getPostsFlow() = cacheDatabase.postsDao.getPostsFlow()

    suspend fun getPosts() = cacheDatabase.postsDao.getPostsList()
    fun getPostFlowById(postId: Int) = cacheDatabase.postsDao.getPostFlowById(postId)
    suspend fun getPostById(postId: Int) = cacheDatabase.postsDao.getPostById(postId)
    suspend fun getPostWithComments(postId: Int) = cacheDatabase.commentsDao.getPostWithComments(postId)

    fun getFavoritePosts() = cacheDatabase.postsDao.getFavoritePosts()


    suspend fun insertPosts(posts: List<PostEntity>) = cacheDatabase.postsDao.insertPosts(posts)
    suspend fun upsertPost(post: PostEntity) = cacheDatabase.postsDao.upsertPost(post)
    suspend fun insertComments(comments: List<CommentEntity>) = cacheDatabase.commentsDao.insertComments(comments)
}