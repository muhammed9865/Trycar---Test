package com.salman.trycar_test.data.util

import com.salman.trycar_test.domain.model.CommentItem

object FakeModel {

    fun fakeCommentItems(startId: Int = 1, count: Int = 4): List<CommentItem> {
        var id = startId
        return List(count) {
            CommentItem(id++, "asdasd", "ms", "ms@gmail.com", "body")
        }
    }
}