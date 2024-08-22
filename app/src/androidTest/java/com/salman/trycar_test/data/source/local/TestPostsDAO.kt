package com.salman.trycar_test.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.salman.trycar_test.data.source.local.dao.PostsDAO
import com.salman.trycar_test.data.source.local.database.CacheDatabase
import com.salman.trycar_test.data.source.local.entity.PostEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostsDAOTest {

    private lateinit var database: CacheDatabase
    private lateinit var postsDao: PostsDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CacheDatabase::class.java
        ).allowMainThreadQueries().build()

        postsDao = database.postsDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertPosts_andGetAllPosts() = runTest {
        val post1 = PostEntity(1, "Title 1", "Body 1")
        val post2 = PostEntity(2, "Title 2", "Body 2")

        postsDao.insertPosts(listOf(post1, post2))

        val postsList = postsDao.getPostsList()
        assertThat(postsList.size, `is`(2))
        assertThat(postsList[0].title, `is`("Title 1"))
        assertThat(postsList[1].title, `is`("Title 2"))
    }

    @Test
    fun upsertPost_andGetById() = runTest {
        val post = PostEntity(1, "Title 1", "Body 1")

        // Insert the post
        postsDao.upsertPost(post)

        // Update the post
        val updatedPost = post.copy(title = "Updated Title 1")
        postsDao.upsertPost(updatedPost)

        val result = postsDao.getPostById(1)
        assertThat(result.title, `is`("Updated Title 1"))
    }

    @Test
    fun insertPost_andGetPostById() = runTest {
        val post = PostEntity(1, "Title 1", "Body 1")
        postsDao.upsertPost(post)

        val result = postsDao.getPostById(1)
        assertThat(result, notNullValue())
        assertThat(result.title, `is`("Title 1"))
    }

    @Test
    fun getPostsFlow_emitsPosts() = runBlocking {
        val post1 = PostEntity(1, "Title 1", "Body 1")
        val post2 = PostEntity(2, "Title 2", "Body 2")
        postsDao.insertPosts(listOf(post1, post2))

        val postsFlow = postsDao.getPostsFlow().first()
        assertThat(postsFlow.size, `is`(2))
        assertThat(postsFlow[0].title, `is`("Title 1"))
        assertThat(postsFlow[1].title, `is`("Title 2"))
    }

    @Test
    fun insertFavoritePosts_andGetFavorites() = runTest {
        val post1 = PostEntity(1, "Title 1", "Body 1", isFavorite = true)
        val post2 = PostEntity(2, "Title 2", "Body 2", isFavorite = false)
        postsDao.insertPosts(listOf(post1, post2))

        val favoritePostsFlow = postsDao.getFavoritePosts().first()
        assertThat(favoritePostsFlow.size, `is`(1))
        assertThat(favoritePostsFlow[0].title, `is`("Title 1"))
        assertThat(favoritePostsFlow[0].isFavorite, `is`(true))
    }



}
