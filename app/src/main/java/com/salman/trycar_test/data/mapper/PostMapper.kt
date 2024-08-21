package com.salman.trycar_test.data.mapper

import com.salman.trycar_test.data.source.local.entity.PostEntity
import com.salman.trycar_test.data.source.remote.dto.PostDTO
import com.salman.trycar_test.domain.model.PostItem

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
fun PostEntity.toDomain(): PostItem {
    return PostItem(
        id = id,
        title = title,
        body = body,
        isFavorite = isFavorite
    )
}

fun PostDTO.toEntity(): PostEntity {
    return PostEntity(
        id = id,
        title = title,
        body = body
    )
}

fun PostDTO.toDomain(): PostItem {
    return PostItem(
        id = id,
        title = title,
        body = body,
        isFavorite = false // Default value
    )
}