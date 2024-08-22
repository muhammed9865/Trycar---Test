package com.salman.trycar_test.data

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.salman.trycar_test.data.mapper.toDomain
import com.salman.trycar_test.data.repository.PostRepositoryImpl
import com.salman.trycar_test.data.source.local.PostsLocalSource
import com.salman.trycar_test.data.source.local.entity.PostEntity
import com.salman.trycar_test.data.source.remote.PostsRemoteSource
import com.salman.trycar_test.data.worker.WorkerConstants
import com.salman.trycar_test.domain.model.PostDetails
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class TestPostRepository {

    private lateinit var postRepository: PostRepositoryImpl
    private val mockRemoteSource: PostsRemoteSource = mock()
    private val mockLocalSource: PostsLocalSource = mock()
    private val mockWorkManager: WorkManager = mock()

    @Before
    fun setup() {
        postRepository = PostRepositoryImpl(mockRemoteSource, mockLocalSource, mockWorkManager)
    }

    @Test
    fun `getFavoritePosts should return flow of favorite PostItems`() = runTest {
        // Given
        val favoritePosts = listOf(PostEntity(1, "Favorite Title", "Favorite Body", true))
        `when`(mockLocalSource.getFavoritePosts()).thenReturn(flowOf(favoritePosts))

        // When
        val result = postRepository.getFavoritePosts().first()

        // Then
        assertEquals(favoritePosts.map { it.toDomain() }, result)
        verify(mockLocalSource).getFavoritePosts()
    }

    @Test
    fun `getPostDetails should return flow of PostDetails`() = runTest {
        // Given
        val postEntity = PostEntity(1, "Post Title", "Post Body", false)
        `when`(mockLocalSource.getPostFlowById(1)).thenReturn(flowOf(postEntity))

        // When
        val result = postRepository.getPostDetails(1).first()

        // Then
        assertEquals(PostDetails(postEntity.toDomain()), result)
        verify(mockLocalSource).getPostFlowById(1)
    }

}
