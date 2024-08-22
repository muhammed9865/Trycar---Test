package com.salman.trycar_test.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.salman.trycar_test.R
import com.salman.trycar_test.domain.model.PostItem
import com.salman.trycar_test.presentation.theme.Dimen
import com.salman.trycar_test.presentation.theme.TrycarTestTheme

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */

@Composable
fun PostsList(
    posts: List<PostItem>,
    emptyListMessage: String,
    actionButtonText: String = stringResource(id = R.string.retry),
    onPostClicked: (PostItem) -> Unit = {},
    onActionClicked: () -> Unit = {},
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = Dimen.contentPadding.dp)
    ) {
        items(posts, key = { it.id }) { post ->
            PostUIItem(post = post, modifier = Modifier.padding(bottom = Dimen.spaceMedium.dp)) {
                onPostClicked(post)
            }
        }
        if (posts.isEmpty()) {
            item {
                EmptyListMessage(
                    message = emptyListMessage,
                    onAction = onActionClicked,
                    buttonText = actionButtonText
                )
            }
        }
    }
}

@Composable
private fun EmptyListMessage(
    modifier: Modifier = Modifier,
    message: String,
    buttonText: String = stringResource(id = R.string.retry),
    onAction: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = message,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        TextButton(onClick = onAction) {
            Text(text = buttonText)
        }
    }
}


@Composable
fun PostUIItem(
    modifier: Modifier = Modifier,
    post: PostItem,
    onClick: (post: PostItem) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable { onClick(post) }
            .padding(Dimen.contentPadding.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ContentText(
            text = post.title,
            style = MaterialTheme.typography.titleSmall
        )
        ContentText(
            text = post.body,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun ContentText(
    text: String,
    style: TextStyle,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = text, style = style,
        maxLines = 2,
        modifier = Modifier.fillMaxWidth(),
        overflow = TextOverflow.Ellipsis,
        color = color
    )
}

@Preview(showBackground = true)
@Composable
private fun PostItemPreview() {
    val post = PostItem(
        1,
        "Hello" +
                "asdasdqw qw dqwdqw eqwe qwd asd qweqwe asdqweqeqweqweqweqweqwdqwd asdqwqweq weqw eqwe qwe",
        "This is a test Test asdawewaeasd asdqwdas dasdasdasda sdwqe qeaqweqwe qw asdasd asd asd asdqwdqwd qsadasdsade",
        isFavorite = false
    )
    TrycarTestTheme {
        PostUIItem(post = post)
    }
}