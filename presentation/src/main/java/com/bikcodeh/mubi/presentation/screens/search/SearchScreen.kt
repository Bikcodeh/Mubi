package com.bikcodeh.mubi.presentation.screens.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bikcodeh.mubi.domain.model.TVShow

@ExperimentalLifecycleComposeApi
@ExperimentalMaterial3Api
@Composable
fun SearchScreen(
    onBack: () -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
    onClickItem: (tvShow: TVShow) -> Unit
) {
    val tvShowsState by searchViewModel.tvShows.collectAsStateWithLifecycle()
    val searchText = rememberSaveable { mutableStateOf("") }

    SearchContent(
        text = searchText.value,
        onTextChange = { searchText.value = it },
        onBack = onBack,
        tvShowsState = tvShowsState,
        onClickItem = onClickItem,
        onSearch = { searchViewModel.searchTvShows(it) }
    )
}