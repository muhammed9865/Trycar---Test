package com.salman.trycar_test.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@Entity("comments")
data class CommentEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val body: String,
    val postId: Int
)
