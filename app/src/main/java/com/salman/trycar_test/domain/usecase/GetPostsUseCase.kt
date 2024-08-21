package com.salman.trycar_test.domain.usecase

import com.salman.trycar_test.domain.model.PostItem
import com.salman.trycar_test.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(): Flow<List<PostItem>> {
        return withContext(Dispatchers.IO) {
            return@withContext postRepository.getPosts()
        }
    }
}