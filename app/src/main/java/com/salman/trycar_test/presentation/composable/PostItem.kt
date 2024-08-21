package com.salman.trycar_test.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.salman.trycar_test.domain.model.PostItem
import com.salman.trycar_test.presentation.theme.Dimen

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@Composable
fun PostItem(
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
        ContentText(text = post.title, style = MaterialTheme.typography.titleSmall)
        ContentText(text = post.body, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun ContentText(
    text: String,
    style: TextStyle,
) {
    Text(
        text = text, style = style,
        maxLines = 2,
        modifier = Modifier.fillMaxWidth(),
        overflow = TextOverflow.Ellipsis,
    )
}