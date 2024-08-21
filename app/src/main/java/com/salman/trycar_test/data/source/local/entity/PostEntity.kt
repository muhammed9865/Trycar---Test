package com.salman.trycar_test.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@Entity("posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val body: String,
    @ColumnInfo("is_favorite")
    val isFavorite: Boolean = false
)
