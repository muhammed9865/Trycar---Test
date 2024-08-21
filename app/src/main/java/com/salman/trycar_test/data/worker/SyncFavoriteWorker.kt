package com.salman.trycar_test.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.salman.trycar_test.data.source.remote.PostsRemoteSource
import com.salman.trycar_test.domain.repository.PostRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@HiltWorker
class SyncFavoriteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val postsRemoteSource: PostsRemoteSource,
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "SyncFavoriteWorker"
    }

    override suspend fun doWork(): Result {
        val postId = inputData.getInt(WorkerConstants.POST_ID, -1)
        if (postId == -1) {
            return Result.failure()
        }

        val newFavoriteState = inputData.getBoolean(WorkerConstants.NEW_FAVORITE_STATE, false)
        updateFavoriteState(postId, newFavoriteState)
        return Result.success()
    }

    private suspend fun updateFavoriteState(postId: Int, newFavoriteState: Boolean) {
        Log.i(TAG, "updatingFavoriteState: postId=$postId, newFavoriteState=$newFavoriteState")
        postsRemoteSource.toggleFavorite(postId, newFavoriteState)
    }
}