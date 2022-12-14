package com.bikcodeh.mubi.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.presentation.screens.home.HomeDefaults
import com.bikcodeh.mubi.presentation.theme.COMMON_PADDING
import com.bikcodeh.mubi.presentation.theme.PADDING_8
import com.bikcodeh.mubi.presentation.theme.backgroundCardColor
import com.bikcodeh.mubi.presentation.theme.textColor
import com.bikcodeh.mubi.presentation.util.Constants


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowItem(tvShow: TVShow, onClickItem: (tvShow: TVShow) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = ShapeDefaults.Medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.backgroundCardColor
        ),
        onClick = { onClickItem(tvShow) },
        modifier = Modifier
            .width(156.dp)
            .height(232.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HomeDefaults.THUMBNAIL_HEIGHT),
                imageUrl = "${Constants.BASE_URL_IMAGES_THUMBNAIL}${tvShow.posterPath}",
                imageDescriptionResId = null,
                imageDescription = tvShow.name,
                contentScale = ContentScale.Crop
            )
            Text(
                text = tvShow.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = COMMON_PADDING,
                        end = COMMON_PADDING,
                        top = HomeDefaults.TITLE_VERTICAL_PADDING,
                        bottom = HomeDefaults.TITLE_VERTICAL_PADDING
                    ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.textColor,
                style = MaterialTheme.typography.titleSmall
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = COMMON_PADDING, end = COMMON_PADDING, bottom = COMMON_PADDING),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingBar(
                    rating = tvShow.voteAverage,
                    stars = 5
                )
                Text(
                    text = if (tvShow.voteAverage > Constants.MAXIMUM_AVERAGE) {
                        "${Constants.MAXIMUM_AVERAGE}"
                    } else {
                        "${tvShow.voteAverage}"
                    },
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.textColor,
                    modifier = Modifier.padding(start = PADDING_8)
                )
            }
        }
    }
}

@Composable
@Preview
fun TvItemPreview() {
    TvShowItem(
        tvShow = TVShow(
            backdropPath = "",
            firstAirDate = "",
            id = "",
            name = "Big Hero 6",
            originalLanguage = "",
            originalName = "",
            overview = "",
            popularity = 0.0,
            posterPath = "",
            voteAverage = 4.0,
            voteCount = 0,
            isFavorite = false,
            category = "",
            emptyList()
        ),
        onClickItem = {}
    )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TvItemPreviewDark() {
    TvShowItem(
        tvShow = TVShow(
            backdropPath = "",
            firstAirDate = "",
            id = "",
            name = "Big Hero 6",
            originalLanguage = "",
            originalName = "",
            overview = "",
            popularity = 0.0,
            posterPath = "",
            voteAverage = 4.0,
            voteCount = 0,
            isFavorite = false,
            category = "",
            seasons = emptyList()
        ),
        onClickItem = {}
    )
}