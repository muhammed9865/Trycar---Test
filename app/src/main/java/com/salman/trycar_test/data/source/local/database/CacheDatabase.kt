package com.salman.trycar_test.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.salman.trycar_test.data.source.local.dao.CommentsDAO
import com.salman.trycar_test.data.source.local.dao.PostsDAO
import com.salman.trycar_test.data.source.local.entity.CommentEntity
import com.salman.trycar_test.data.source.local.entity.PostEntity

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@Database(
    entities = [
        CommentEntity::class,
        PostEntity::class,
    ],
    version = 1
)
abstract class CacheDatabase : RoomDatabase() {
    abstract val postsDao: PostsDAO
    abstract val commentsDao: CommentsDAO
}