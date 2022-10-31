package com.bikcodeh.mubi.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bikcodeh.mubi.presentation.components.ErrorScreen
import com.bikcodeh.mubi.presentation.components.LoadingScreen
import com.bikcodeh.mubi.presentation.theme.backgroundColor

@ExperimentalLifecycleComposeApi
@ExperimentalMaterial3Api
@Composable
fun DetailScreen(
    tvShowId: String,
    category: String,
    isFavorite: Boolean,
    onBack: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel(),
    onSeasonClick: (tvShowId: String, seasonNumber: Int) -> Unit
) {
    LaunchedEffect(key1 = tvShowId) {
        detailViewModel.getDetailById(tvShowId, category, isFavorite)
    }

    val detailState by detailViewModel.detailState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    if (detailState.isLoading) {
        LoadingScreen(
            modifier = Modifier.fillMaxSize()
        )
    } else {
        Scaffold(
        ) { paddingValues ->
            detailState.error?.let {
                ErrorScreen(
                    modifier = Modifier.fillMaxSize(),
                    messageId = it.errorMessage,
                    onTryAgain = {
                        detailViewModel.getDetailById(
                            tvShowId,
                            category,
                            isFavorite
                        )
                    },
                    displayTryButton = it.displayTryAgainBtn
                )
            }
            detailState.tvShow?.let { tvShow ->
                DetailContent(
                    tvShow = tvShow,
                    scrollState = scrollState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                        .background(color = MaterialTheme.colorScheme.backgroundColor),
                    setAsFavorite = {
                        detailViewModel.setIsFavorite(
                            isFavorite = it,
                            tvShowId = tvShow.id
                        )
                    },
                    onBack = onBack,
                    onSeasonClick = onSeasonClick
                )
            }
        }
    }
}