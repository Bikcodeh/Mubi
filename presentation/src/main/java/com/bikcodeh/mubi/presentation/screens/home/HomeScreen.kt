package com.bikcodeh.mubi.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.presentation.components.LoadingScreen
import com.bikcodeh.mubi.presentation.screens.home.components.HomeTopBar
import com.bikcodeh.mubi.presentation.theme.backgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onClickItem: (tvShow: TVShow) -> Unit
) {

    val tvShows = homeViewModel.tvShows.collectAsLazyPagingItems()
    val result = handlePagingResult(tvShows = tvShows)
    var isLoading by remember { mutableStateOf(false) }
    isLoading = tvShows.loadState.append is LoadState.Loading

    if (tvShows.loadState.refresh is LoadState.Loading) {
        LoadingScreen(modifier = Modifier.fillMaxSize())
    }

    if (result) {

    } else {
        Scaffold(
            topBar = {
                HomeTopBar(onSearchClick = {}, onProfileClick = {})
            }
        ) { paddingValues ->
            HomeContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
                    .background(MaterialTheme.colorScheme.backgroundColor),
                tvShows = tvShows,
                onClickItem = onClickItem,
                isLoading = isLoading
            )
        }
    }
}

@Composable
fun handlePagingResult(
    tvShows: LazyPagingItems<TVShow>
): Boolean {
    tvShows.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }
        return error != null
    }
}