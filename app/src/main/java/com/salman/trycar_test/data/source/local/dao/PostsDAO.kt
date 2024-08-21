package com.salman.trycar_test.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.salman.trycar_test.data.source.local.entity.PostEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@Dao
interface PostsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Upsert
    suspend fun upsertPost(post: PostEntity)

    @Query("SELECT * FROM posts")
    fun getPostsFlow(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPostFlowById(id: Int): Flow<PostEntity>

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPostById(id: Int): PostEntity

    // 1 is true, 0 is false
    @Query("SELECT * FROM posts WHERE is_favorite = 1")
    fun getFavoritePosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts")
    suspend fun getPostsList(): List<PostEntity>

}