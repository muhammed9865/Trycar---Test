package com.salman.trycar_test.data

import com.salman.trycar_test.data.repository.FakeCommentsRepositoryImpl
import com.salman.trycar_test.data.util.FakeModel
import com.salman.trycar_test.domain.repository.CommentsRepository
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TestCommentsRepository {

    lateinit var commentsRepository: CommentsRepository


    @Before
    fun setup() {
        commentsRepository = FakeCommentsRepositoryImpl()
    }

    @Test
    fun `test fetch post comments returns cached and remote comments`() = runTest {
        // Given
        val postId = 1
        val expectedCacheResult = FakeModel.fakeCommentItems(1)
        val expectedRemoteResult = FakeModel.fakeCommentItems(5)

        // When
        val results = commentsRepository.getPostComments(postId).take(2).toList()

        // Then
        assert(results.first() == expectedCacheResult)
        assert(results[1] == expectedRemoteResult)
    }
}