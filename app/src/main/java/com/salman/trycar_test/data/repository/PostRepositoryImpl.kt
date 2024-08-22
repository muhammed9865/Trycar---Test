package com.salman.trycar_test.data.repository

import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.salman.trycar_test.data.mapper.toDomain
import com.salman.trycar_test.data.mapper.toEntity
import com.salman.trycar_test.data.source.local.PostsLocalSource
import com.salman.trycar_test.data.source.remote.PostsRemoteSource
import com.salman.trycar_test.data.source.remote.dto.PostDTO
import com.salman.trycar_test.data.worker.SyncFavoriteWorker
import com.salman.trycar_test.data.worker.WorkerConstants
import com.salman.trycar_test.domain.model.PostDetails
import com.salman.trycar_test.domain.model.PostItem
import com.salman.trycar_test.domain.repository.PostRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
class PostRepositoryImpl @Inject constructor(
    private val remoteSource: PostsRemoteSource,
    private val localSource: PostsLocalSource,
    private val workManager: WorkManager,
) : PostRepository {
    override suspend fun getPosts(): Flow<Result<List<PostItem>>> {
        return coroutineScope {
            channelFlow {
                val cachedPosts = localSource.getPosts()
                if (cachedPosts.isNotEmpty())
                    send(Result.success(cachedPosts.map { it.toDomain() }))


                // Update cache with remote posts
                val remotePosts = remoteSource.getPosts()
                remotePosts.onSuccess { posts ->
                    cacheRemotePosts(posts)
                    send(Result.success(posts.map { it.toDomain() }))
                }.onFailure {
                    send(Result.failure(it))
                }

                // Indicate that the flow has completed
                close()
            }
        }
    }

    override suspend fun getFavoritePosts(): Flow<List<PostItem>> {
        return localSource.getFavoritePosts().map { posts ->
            posts.map { it.toDomain() }
        }
    }

    override suspend fun getPostDetails(postId: Int): Flow<PostDetails> {
        return localSource.getPostFlowById(postId).map { PostDetails(it.toDomain()) }
    }

    override suspend fun toggleFavorite(postId: Int) {
        val post = localSource.getPostById(postId)
        val newFavoriteState = !post.isFavorite
        localSource.upsertPost(post.copy(isFavorite = newFavoriteState))
        syncPostFavoriteStatus(postId, newFavoriteState)
    }

    private suspend fun cacheRemotePosts(posts: List<PostDTO>) {
        localSource.insertPosts(posts.map { it.toEntity() })
    }

    private fun syncPostFavoriteStatus(postId: Int, newFavoriteState: Boolean) {
        val inputData = workDataOf(
            WorkerConstants.POST_ID to postId,
            WorkerConstants.NEW_FAVORITE_STATE to newFavoriteState
        )
        val syncWorker = OneTimeWorkRequestBuilder<SyncFavoriteWorker>()
            .setConstraints(
                Constraints(
                    requiredNetworkType = NetworkType.CONNECTED,
                )
            )
            .setInputData(inputData)
            .build()

        val workerUniqueName = WorkerConstants.SYNC_FAVORITE_WORKER + postId
        workManager.beginUniqueWork(
            workerUniqueName,
            ExistingWorkPolicy.REPLACE,
            syncWorker
        ).enqueue()
    }
}