package com.salman.trycar_test.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.salman.trycar_test.presentation.navigation.Routes
import com.salman.trycar_test.presentation.screen.favorites.FavoritesScreen
import com.salman.trycar_test.presentation.screen.posts.PostsScreen
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 8/21/2024.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState {
        Routes.HOME.tabs.size
    }
    val selectedTabIndex by remember {
        derivedStateOf { pagerState.currentPage }
    }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Routes.HOME.tabs.forEachIndexed { index, tab ->
                val isSelected = selectedTabIndex == index
                Tab(
                    selected = isSelected,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = stringResource(id = tab.title)) },
                    icon = {
                        Icon(
                            imageVector = tab.icon(isSelected),
                            contentDescription = stringResource(
                                id = tab.title
                            ),
                        )
                    }
                )
            }
        }

        HorizontalPager(state = pagerState) { currentPage ->
            when (currentPage) {
                0 -> {
                    PostsScreen()
                }

                1 -> {
                    FavoritesScreen()
                }
            }
        }
    }
}