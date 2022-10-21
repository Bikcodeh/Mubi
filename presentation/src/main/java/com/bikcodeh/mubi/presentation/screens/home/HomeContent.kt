package com.bikcodeh.mubi.presentation.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.LazyPagingItems
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.presentation.components.CoilImage
import com.bikcodeh.mubi.presentation.components.LoadingScreen
import com.bikcodeh.mubi.presentation.components.RatingBar
import com.bikcodeh.mubi.presentation.screens.home.HomeDefaults.THUMBNAIL_HEIGHT
import com.bikcodeh.mubi.presentation.theme.COMMON_PADDING
import com.bikcodeh.mubi.presentation.theme.FONT_NORMAL
import com.bikcodeh.mubi.presentation.theme.backgroundCardColor
import com.bikcodeh.mubi.presentation.theme.textColor
import com.bikcodeh.mubi.presentation.util.Constants
import com.bikcodeh.mubi.presentation.util.Constants.MAXIMUM_AVERAGE
import com.bikcodeh.mubi.presentation.util.extension.items

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    tvShows: LazyPagingItems<TVShow>,
    onClickItem: (tvShow: TVShow) -> Unit,
    isLoading: Boolean
) {
    Column() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier,
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = tvShows,
                key = { tvShow -> tvShow.id }

            ) { tvShow: TVShow? ->
                tvShow?.let {
                    TvItem(it, onClickItem = onClickItem)
                }
            }
        }
        if (isLoading) {
            LoadingScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvItem(tvShow: TVShow, onClickItem: (tvShow: TVShow) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = ShapeDefaults.Medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.backgroundCardColor
        ),
        onClick = { onClickItem(tvShow) }
    ) {
        Column {

            CoilImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(THUMBNAIL_HEIGHT),
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
                fontSize = FONT_NORMAL,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.textColor
            )
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = COMMON_PADDING,
                        end = COMMON_PADDING,
                        bottom = COMMON_PADDING
                    )
            ) {
                val (rating, ratingValue) = createRefs()
                val voteAverage =
                    if (tvShow.voteAverage > MAXIMUM_AVERAGE) MAXIMUM_AVERAGE else tvShow.voteAverage
                RatingBar(
                    modifier = Modifier.constrainAs(rating) {
                        start.linkTo(parent.start)
                        linkTo(parent.top, parent.bottom)
                    },
                    rating = voteAverage,
                    stars = 5
                )
                Text(
                    text = "$voteAverage",
                    fontSize = FONT_NORMAL,
                    color = MaterialTheme.colorScheme.textColor,
                    modifier = Modifier.constrainAs(ratingValue) {
                        linkTo(rating.top, rating.bottom)
                        start.linkTo(rating.end, margin = COMMON_PADDING)
                    })
            }
        }
    }
}

@Composable
@Preview
fun TvItemPreview() {
    TvItem(
        tvShow = TVShow(
            backdropPath = "",
            firstAirDate = "",
            id = "",
            name = "Breaking Bad",
            originalLanguage = "",
            originalName = "",
            overview = "",
            popularity = 0.0,
            posterPath = "",
            voteAverage = 4.0,
            voteCount = 0,
            isFavorite = false,
            category = ""
        ),
        onClickItem = {}
    )
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun TvItemPreviewDark() {
    TvItem(
        tvShow = TVShow(
            backdropPath = "",
            firstAirDate = "",
            id = "",
            name = "Breaking Bad",
            originalLanguage = "",
            originalName = "",
            overview = "",
            popularity = 0.0,
            posterPath = "",
            voteAverage = 4.0,
            voteCount = 0,
            isFavorite = false,
            category = ""
        ),
        onClickItem = {}
    )
}