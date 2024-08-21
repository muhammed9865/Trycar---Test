package com.salman.trycar_test.data.di

import com.salman.trycar_test.data.repository.CommentsRepositoryImpl
import com.salman.trycar_test.data.repository.PostRepositoryImpl
import com.salman.trycar_test.domain.repository.CommentsRepository
import com.salman.trycar_test.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository

    @Binds
    fun bindCommentsRepository(commentsRepositoryImpl: CommentsRepositoryImpl): CommentsRepository
}