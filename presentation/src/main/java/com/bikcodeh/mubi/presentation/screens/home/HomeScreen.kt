package com.bikcodeh.mubi.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bikcodeh.mubi.domain.common.toError
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.domain.model.getTvShowType
import com.bikcodeh.mubi.presentation.components.ErrorScreen
import com.bikcodeh.mubi.presentation.components.handleError
import com.bikcodeh.mubi.presentation.screens.home.components.HomeTopBar
import com.bikcodeh.mubi.presentation.theme.backgroundColor
import com.bikcodeh.mubi.presentation.util.ErrorLoadState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onClickItem: (tvShow: TVShow) -> Unit,
    navigateToProfile: () -> Unit,
    navigateToSearch: () -> Unit
) {
    val selectedCTvShowType = rememberSaveable { mutableStateOf<TvShowType>(TvShowType.POPULAR) }

    LaunchedEffect(key1 = selectedCTvShowType.value) {
        homeViewModel.searchTvShows(tvShowType = selectedCTvShowType.value)
    }

    val tvShows = homeViewModel.tvShows.collectAsState().value.collectAsLazyPagingItems()
    val result = handlePagingResult(tvShows = tvShows)

    Scaffold(
        topBar = {
            HomeTopBar(onSearchClick = navigateToSearch, onProfileClick = navigateToProfile)
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

            HomeContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
                    .background(MaterialTheme.colorScheme.backgroundColor),
                tvShows = tvShows,
                onClickItem = onClickItem,
                errorState = result,
                isLoading = tvShows.loadState.refresh is LoadState.Loading,
                selectedCTvShowType = selectedCTvShowType.value,
                onSelectionChange = { tvShowTypeName ->
                    getTvShowType(tvShowTypeName)?.let { tvShowType ->
                        selectedCTvShowType.value = tvShowType
                    }
                }
            )
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