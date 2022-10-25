@file:OptIn(ExperimentalMaterial3Api::class)

package com.bikcodeh.mubi.presentation.screens.search

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.components.CoilImage
import com.bikcodeh.mubi.presentation.components.ErrorScreen
import com.bikcodeh.mubi.presentation.components.LoadingScreen
import com.bikcodeh.mubi.presentation.components.RatingBar
import com.bikcodeh.mubi.presentation.theme.*
import com.bikcodeh.mubi.presentation.util.Constants
import com.bikcodeh.mubi.presentation.util.extension.validateRating

@ExperimentalMaterial3Api
@Composable
fun SearchContent(
    text: String,
    onTextChange: (value: String) -> Unit,
    onBack: () -> Unit,
    tvShowsState: SearchState,
    onClickItem: (tvShow: TVShow) -> Unit,
    onSearch: (text: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.backgroundColor)
    ) {
        SearchTopBar(
            text = text,
            onTextChange = onTextChange,
            onBack = onBack,
            onSearch = onSearch
        )
        when (tvShowsState) {
            SearchState.Idle -> {
                SearchGenericMessage(messageResId = R.string.what_would_you_like_to_see)
            }
            is SearchState.Success -> {
                if (tvShowsState.tvShows.isEmpty()) {
                    SearchGenericMessage(messageResId = R.string.empty_search)
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = COMMON_PADDING,
                                end = COMMON_PADDING,
                                top = COMMON_PADDING
                            ),
                        verticalArrangement = Arrangement.spacedBy(PADDING_8)
                    ) {
                        items(tvShowsState.tvShows) { tvShow ->
                            SearchItem(
                                tvShow = tvShow,
                                onClickItem = onClickItem
                            )
                        }
                    }
                }
            }
            is SearchState.Failure -> {
                tvShowsState.error?.let {
                    if (it.notFoundError) {
                        SearchGenericMessage(messageResId = it.errorMessage)
                    } else {
                        ErrorScreen(
                            messageId = it.errorMessage,
                            onTryAgain = { onSearch(text) },
                            displayTryButton = it.displayTryAgainBtn
                        )
                    }
                }
            }
            SearchState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun SearchGenericMessage(
    @StringRes messageResId: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.backgroundColor),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = COMMON_PADDING),
            text = stringResource(id = messageResId),
            color = MaterialTheme.colorScheme.textColor,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, heightDp = 500)
@Composable
fun SearchGenericMessagePreview() {
    SearchGenericMessage(R.string.what_would_you_like_to_see)
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, heightDp = 500)
@Composable
fun SearchGenericMessagePreviewDark() {
    SearchGenericMessage(R.string.what_would_you_like_to_see)
}

@ExperimentalMaterial3Api
@Composable
fun SearchItem(
    tvShow: TVShow,
    onClickItem: (tvShow: TVShow) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = DEFAULT_ELEVATION
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.backgroundCardColor
        ),
        shape = MaterialTheme.shapes.medium,
        onClick = { onClickItem(tvShow) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(CARD_HEIGHT_SEARCH_ITEM)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(IMAGE_WIDTH_SEARCH_ITEM)
            ) {
                CoilImage(
                    imageUrl = "${Constants.BASE_URL_IMAGES_THUMBNAIL}${tvShow.posterPath}",
                    imageDescriptionResId = null,
                    imageDescription = tvShow.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = COMMON_PADDING)
            ) {
                Text(
                    text = tvShow.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = COMMON_PADDING),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBar(
                        rating = tvShow.voteAverage,
                        modifier = Modifier.padding(end = PADDING_8)
                    )
                    Text(
                        text = "${tvShow.voteAverage.validateRating()}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.textColor
                    )
                }
                Text(
                    text = tvShow.overview,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.textColor,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(top = PADDING_8)
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = false)
@Composable
fun SearchItemPreview() {
    SearchItem(
        tvShow = TVShow(
            backdropPath = "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
            firstAirDate = "",
            id = "",
            name = "Breaking Bad",
            originalLanguage = "",
            originalName = "",
            overview = "Big Hero 6 The Series\" takes place after the events of the film \"Big Hero 6\" and continues the adventures of 14-year-old tech genius Hiro Hamada. He is joined by the compassionate robot Baymax.",
            popularity = 0.0,
            posterPath = "",
            voteAverage = 3.0,
            voteCount = 0,
            isFavorite = false,
            category = "",
            seasons = emptyList()
        ),
        onClickItem = {}
    )
}

@ExperimentalMaterial3Api
@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchItemPreviewDark() {
    SearchItem(
        tvShow = TVShow(
            backdropPath = "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
            firstAirDate = "",
            id = "",
            name = "Breaking Bad",
            originalLanguage = "",
            originalName = "",
            overview = "Big Hero 6 The Series\" takes place after the events of the film \"Big Hero 6\" and continues the adventures of 14-year-old tech genius Hiro Hamada. He is joined by the compassionate robot Baymax.",
            popularity = 0.0,
            posterPath = "",
            voteAverage = 3.0,
            voteCount = 0,
            isFavorite = false,
            category = "",
            seasons = emptyList()
        ),
        onClickItem = {}
    )
}

@Preview(showBackground = true, heightDp = 600)
@Composable
fun SearchContentPreview() {
    SearchContent(
        text = "",
        onTextChange = {},
        onBack = {},
        onClickItem = {},
        tvShowsState = SearchState.Success(
            tvShows = listOf(
                TVShow(
                    backdropPath = "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
                    firstAirDate = "",
                    id = "",
                    name = "Breaking Bad",
                    originalLanguage = "",
                    originalName = "",
                    overview = "Big Hero 6 The Series\" takes place after the events of the film \"Big Hero 6\" and continues the adventures of 14-year-old tech genius Hiro Hamada. He is joined by the compassionate robot Baymax.",
                    popularity = 0.0,
                    posterPath = "",
                    voteAverage = 3.0,
                    voteCount = 0,
                    isFavorite = false,
                    category = "",
                    seasons = emptyList()
                ),
                TVShow(
                    backdropPath = "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
                    firstAirDate = "",
                    id = "",
                    name = "Breaking Bad",
                    originalLanguage = "",
                    originalName = "",
                    overview = "Big Hero 6 The Series\" takes place after the events of the film \"Big Hero 6\" and continues the adventures of 14-year-old tech genius Hiro Hamada. He is joined by the compassionate robot Baymax.",
                    popularity = 0.0,
                    posterPath = "",
                    voteAverage = 3.0,
                    voteCount = 0,
                    isFavorite = false,
                    category = "",
                    seasons = emptyList()
                ),
                TVShow(
                    backdropPath = "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
                    firstAirDate = "",
                    id = "",
                    name = "Breaking Bad",
                    originalLanguage = "",
                    originalName = "",
                    overview = "Big Hero 6 The Series\" takes place after the events of the film \"Big Hero 6\" and continues the adventures of 14-year-old tech genius Hiro Hamada. He is joined by the compassionate robot Baymax.",
                    popularity = 0.0,
                    posterPath = "",
                    voteAverage = 3.0,
                    voteCount = 0,
                    isFavorite = false,
                    category = "",
                    seasons = emptyList()
                )
            )
        ),
        onSearch = {}
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, heightDp = 600)
@Composable
fun SearchContentPreviewDark() {
    SearchContent(
        text = "",
        onTextChange = {},
        onBack = {},
        tvShowsState = SearchState.Success(
            tvShows = listOf(
                TVShow(
                    backdropPath = "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
                    firstAirDate = "",
                    id = "",
                    name = "Breaking Bad",
                    originalLanguage = "",
                    originalName = "",
                    overview = "Big Hero 6 The Series\" takes place after the events of the film \"Big Hero 6\" and continues the adventures of 14-year-old tech genius Hiro Hamada. He is joined by the compassionate robot Baymax.",
                    popularity = 0.0,
                    posterPath = "",
                    voteAverage = 3.0,
                    voteCount = 0,
                    isFavorite = false,
                    category = "",
                    seasons = emptyList()
                ),
                TVShow(
                    backdropPath = "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
                    firstAirDate = "",
                    id = "",
                    name = "Breaking Bad",
                    originalLanguage = "",
                    originalName = "",
                    overview = "Big Hero 6 The Series\" takes place after the events of the film \"Big Hero 6\" and continues the adventures of 14-year-old tech genius Hiro Hamada. He is joined by the compassionate robot Baymax.",
                    popularity = 0.0,
                    posterPath = "",
                    voteAverage = 3.0,
                    voteCount = 0,
                    isFavorite = false,
                    category = "",
                    seasons = emptyList()
                ),
                TVShow(
                    backdropPath = "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
                    firstAirDate = "",
                    id = "",
                    name = "Breaking Bad",
                    originalLanguage = "",
                    originalName = "",
                    overview = "Big Hero 6 The Series\" takes place after the events of the film \"Big Hero 6\" and continues the adventures of 14-year-old tech genius Hiro Hamada. He is joined by the compassionate robot Baymax.",
                    popularity = 0.0,
                    posterPath = "",
                    voteAverage = 3.0,
                    voteCount = 0,
                    isFavorite = false,
                    category = "",
                    seasons = emptyList()
                )
            )
        ),
        onClickItem = {},
        onSearch = {}
    )
}