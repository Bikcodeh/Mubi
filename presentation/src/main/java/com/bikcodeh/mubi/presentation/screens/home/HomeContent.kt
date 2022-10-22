package com.bikcodeh.mubi.presentation.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.LazyPagingItems
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.components.CoilImage
import com.bikcodeh.mubi.presentation.components.RatingBar
import com.bikcodeh.mubi.presentation.screens.home.HomeDefaults.COLUMNS_ITEM
import com.bikcodeh.mubi.presentation.screens.home.HomeDefaults.PADDING_ITEM
import com.bikcodeh.mubi.presentation.screens.home.HomeDefaults.SPACING_ITEM
import com.bikcodeh.mubi.presentation.screens.home.HomeDefaults.THUMBNAIL_HEIGHT
import com.bikcodeh.mubi.presentation.theme.*
import com.bikcodeh.mubi.presentation.util.Constants
import com.bikcodeh.mubi.presentation.util.Constants.MAXIMUM_AVERAGE
import com.bikcodeh.mubi.presentation.util.ErrorLoadState
import com.bikcodeh.mubi.presentation.util.extension.items

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    tvShows: LazyPagingItems<TVShow>,
    onClickItem: (tvShow: TVShow) -> Unit,
    errorState: ErrorLoadState
) {
    Column(modifier = modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(COLUMNS_ITEM),
            contentPadding = PaddingValues(PADDING_ITEM),
            horizontalArrangement = Arrangement.spacedBy(SPACING_ITEM),
            verticalArrangement = Arrangement.spacedBy(SPACING_ITEM)
        ) {
            items(
                items = tvShows,
                key = { tvShow -> tvShow.name }

            ) { tvShow: TVShow? ->
                tvShow?.let {
                    TvItem(it, onClickItem = onClickItem)
                }
            }
            item(span = { GridItemSpan(2) }) {
                if (errorState.isAppend) {
                    ErrorMoreRetry(
                        onRetry = { tvShows.retry() }
                    )
                }
            }
        }

    }
}

@Composable
fun ErrorMoreRetry(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.backgroundColor),
    ) {
        Text(
            text = stringResource(id = R.string.error_fetching_data),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = COMMON_PADDING, end = COMMON_PADDING, top = COMMON_PADDING),
            color = MaterialTheme.colorScheme.textColor,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { onRetry() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(COMMON_PADDING),
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(3.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VeryLightBlue
            )
        ) {
            Text(
                text = stringResource(id = R.string.try_again).uppercase(),
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorMoreRetryPreview() {
    ErrorMoreRetry(onRetry = {})
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ErrorMoreRetryPreviewDark() {
    ErrorMoreRetry(onRetry = {})
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
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.textColor,
                style = MaterialTheme.typography.titleSmall
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
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.textColor,
                    modifier = Modifier.constrainAs(ratingValue) {
                        linkTo(rating.top, rating.bottom)
                        start.linkTo(rating.end, margin = MEDIUM_PADDING)
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
            name = "Big Hero 6",
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
            name = "Big Hero 6",
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