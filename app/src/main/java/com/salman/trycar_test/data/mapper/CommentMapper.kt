package com.salman.trycar_test.data.mapper

import com.salman.trycar_test.data.source.local.entity.CommentEntity
import com.salman.trycar_test.data.source.remote.dto.CommentDTO
import com.salman.trycar_test.data.util.ImageGenerator
import com.salman.trycar_test.domain.model.CommentItem

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
fun CommentEntity.toDomain(): CommentItem {
    return CommentItem(
        id = id,
        name = name,
        email = email,
        body = body,
        userImage = ImageGenerator.generate(uniqueNumber = id)
    )
}

fun CommentDTO.toEntity(): CommentEntity {
    return CommentEntity(
        id = id,
        postId = postId,
        name = name,
        email = email,
        body = body
    )
}

fun CommentDTO.toDomain(): CommentItem {
    return CommentItem(
        id = id,
        name = name,
        email = email,
        body = body,
        userImage = ImageGenerator.generate(uniqueNumber = id)
    )
}