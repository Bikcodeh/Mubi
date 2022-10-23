package com.bikcodeh.mubi.presentation.screens.search

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.presentation.components.CoilImage
import com.bikcodeh.mubi.presentation.components.RatingBar
import com.bikcodeh.mubi.presentation.theme.*
import com.bikcodeh.mubi.presentation.util.Constants
import com.bikcodeh.mubi.presentation.util.extension.validateRating

@Composable
fun SearchContent(
    text: String,
    onTextChange: (value: String) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.backgroundColor)
    ) {
        SearchTopBar(
            text = text,
            onTextChange = onTextChange,
            onBack = onBack
        )

    }
}

@ExperimentalMaterial3Api
@Composable
fun SearchItem(
    tvShow: TVShow,
    onClickItem: (tvShow: TVShow) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
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
                .height(147.dp)
        ) {
            CoilImage(
                imageUrl = "${Constants.BASE_URL_IMAGES_THUMBNAIL}${tvShow.posterPath}",
                imageDescriptionResId = null,
                imageDescription = tvShow.name,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(112.dp)
            )
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = tvShow.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            end = COMMON_PADDING,
                            top = COMMON_PADDING
                        ),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.textColor
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBar(
                        modifier = Modifier
                            .padding(end = PADDING_8),
                        rating = tvShow.voteAverage,
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
                    modifier = Modifier.padding(top = PADDING_8, end = COMMON_PADDING)
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
            category = ""
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
            category = ""
        ),
        onClickItem = {}
    )
}

@Preview(showBackground = true, heightDp = 600)
@Composable
fun SearchContentPreview() {
    SearchContent(text = "", onTextChange = {}, onBack = {})
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, heightDp = 600)
@Composable
fun SearchContentPreviewDark() {
    SearchContent(text = "", onTextChange = {}, onBack = {})
}