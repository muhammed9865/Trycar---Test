package com.salman.trycar_test.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.salman.trycar_test.data.source.local.dao.CommentsDAO
import com.salman.trycar_test.data.source.local.dao.PostsDAO
import com.salman.trycar_test.data.source.local.database.CacheDatabase
import com.salman.trycar_test.data.source.local.entity.CommentEntity
import com.salman.trycar_test.data.source.local.entity.PostEntity
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestCommentsDAO {
    private lateinit var database: CacheDatabase
    private lateinit var commentsDao: CommentsDAO
    private lateinit var postsDao: PostsDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CacheDatabase::class.java
        ).allowMainThreadQueries().build()

        commentsDao = database.commentsDao
        postsDao = database.postsDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertComments_andGetPostWithComments() = runTest {
        // Insert a post
        val post = PostEntity(1, "Post Title", "Post Body")
        postsDao.upsertPost(post)

        // Insert comments for the post
        val comment1 = CommentEntity(1, "User1", "user1@example.com", "Comment 1", postId = 1)
        val comment2 = CommentEntity(2, "User2", "user2@example.com", "Comment 2", postId = 1)
        commentsDao.insertComments(listOf(comment1, comment2))

        // Retrieve the post with comments
        val postWithComments = commentsDao.getPostWithComments(postId = 1)

        // Assertions
        assertThat(postWithComments, notNullValue())
        assertThat(postWithComments.post.title, `is`("Post Title"))
        assertThat(postWithComments.comments.size, `is`(2))
        assertThat(postWithComments.comments[0].body, `is`("Comment 1"))
        assertThat(postWithComments.comments[1].body, `is`("Comment 2"))
    }

}