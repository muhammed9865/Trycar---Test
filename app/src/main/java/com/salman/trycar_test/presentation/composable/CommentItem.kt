package com.salman.trycar_test.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.salman.trycar_test.domain.model.CommentItem
import com.salman.trycar_test.presentation.theme.TrycarTestTheme

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@Composable
fun CommentUIItem(
    modifier: Modifier = Modifier,
    comment: CommentItem,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UserInfo(name = comment.name, email = comment.email, imageUrl = comment.userImage)
        Text(
            text = comment.body,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun UserInfo(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    imageUrl: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = "User image",
            loading = { CircularProgressIndicator(
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round
            ) },
            modifier = Modifier.size(45.dp).clip(CircleShape),

        )
        InfoTexts(name = name, email = email)
    }
}

@Composable
fun InfoTexts(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = name, style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = email, style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CommentItemPreview() {
    TrycarTestTheme {
        CommentUIItem(
            comment = CommentItem(
                id = 1,
                userImage = "https://i.pravatar.cc/ ",
                name = "Muhammed Salman",
                email = "ms@slman.com",
                body = "This is a comment body"
            )
        )
    }

}