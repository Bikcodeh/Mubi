package com.bikcodeh.mubi.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bikcodeh.mubi.domain.entity.TvShowEntity
import timber.log.Timber

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    val tvShows = homeViewModel.tvShows.collectAsLazyPagingItems()

    val result = handlePagingResult(tvShows = tvShows)

    if (result) {
        Timber.d("BIEN VAMOS BIEN")
    } else {
        Timber.d("DATA ${tvShows.itemCount}")
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                items = tvShows,
                key = { tvShow -> tvShow.id}
            ) { value: TvShowEntity? ->
                value?.let {
                    TvItem(it.name)
                }
            }
        }
        /*LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tvShows.itemCount) { index ->
                TvItem(index)
            }
        }*/
    }
}

@Composable
fun handlePagingResult(
    tvShows: LazyPagingItems<TvShowEntity>
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

@Composable
fun TvItem(index: String) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = ShapeDefaults.Medium
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color = Color.Blue)
            )
            Text(
                text = index,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
@Preview
fun TvItemPreview() {
    TvItem("")
}