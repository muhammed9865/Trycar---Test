package com.salman.trycar_test.domain.model

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
data class PostDetails(
    val post: PostItem,
) {
    val isFavorite: Boolean = post.isFavorite
}
