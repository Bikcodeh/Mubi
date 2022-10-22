package com.bikcodeh.mubi.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bikcodeh.mubi.domain.common.Error
import com.bikcodeh.mubi.domain.common.toError
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.presentation.components.ErrorScreen
import com.bikcodeh.mubi.presentation.components.LoadingScreen
import com.bikcodeh.mubi.presentation.components.handleError
import com.bikcodeh.mubi.presentation.screens.home.components.HomeTopBar
import com.bikcodeh.mubi.presentation.theme.backgroundColor
import com.bikcodeh.mubi.presentation.util.ErrorLoadState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onClickItem: (tvShow: TVShow) -> Unit
) {
    val tvShows = homeViewModel.tvShows.collectAsLazyPagingItems()
    val result = handlePagingResult(tvShows = tvShows)

    Scaffold(
        topBar = {
            HomeTopBar(onSearchClick = {}, onProfileClick = {})
        }
    ) { paddingValues ->
        if (result.isRefresh) {
            result.error?.let {
                ErrorScreen(
                    messageId = handleError(error = it),
                    modifier = Modifier.fillMaxSize()
                )
            }
        } else {
            if (tvShows.loadState.refresh is LoadState.Loading) {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            } else {
                HomeContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                        .background(MaterialTheme.colorScheme.backgroundColor),
                    tvShows = tvShows,
                    onClickItem = onClickItem,
                    errorState = result
                )
            }
        }
    }
}

@Composable
fun handlePagingResult(
    tvShows: LazyPagingItems<TVShow>
): ErrorLoadState {
    val errorLoadState = ErrorLoadState()
    tvShows.apply {
        val stateError = when {
            loadState.refresh is LoadState.Error -> {
                errorLoadState.isRefresh = true
                loadState.refresh as LoadState.Error
            }
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> {
                errorLoadState.isAppend = true
                loadState.append as LoadState.Error
            }
            else -> null
        }
        val error = stateError?.error?.toError()
        errorLoadState.error = error
        return errorLoadState
    }
}