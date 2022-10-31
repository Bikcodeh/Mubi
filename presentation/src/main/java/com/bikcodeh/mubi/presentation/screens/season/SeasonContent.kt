package com.bikcodeh.mubi.presentation.screens.season

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.bikcodeh.mubi.domain.model.Episode
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.components.CoilImage
import com.bikcodeh.mubi.presentation.theme.*
import com.bikcodeh.mubi.presentation.util.Constants
import com.bikcodeh.mubi.presentation.util.Util

@ExperimentalMaterial3Api
@Composable
fun SeasonContent(
    seasonState: SeasonState,
    modifier: Modifier = Modifier

) {
    LazyColumn(
        modifier = modifier
    ) {
        seasonState.season?.episodes?.let { episodes ->
            items(episodes) { episode ->
                DetailEpisodeItem(episode = episode)
            }
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun DetailEpisodeItem(
    episode: Episode
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = DEFAULT_ELEVATION
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.backgroundCardColor
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(horizontal = COMMON_PADDING, vertical = PADDING_4)
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
                    imageUrl = "${Constants.BASE_URL_IMAGES_THUMBNAIL}${episode.stillPath}",
                    imageDescriptionResId = null,
                    imageDescription = episode.name,
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
                    text = stringResource(id = R.string.episode_number, episode.episodeNumber),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = COMMON_PADDING),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = episode.overview,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.textColor,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = PADDING_8)
                )
                Text(
                    text = Util.formatRunTime(episode.runtime),
                    color = VeryLightBlue,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun DetailSeasonItemPreview(
) {
    DetailEpisodeItem(
        episode = Episode(
            id = 10,
            name = "Android test",
            seasonNumber = "1",
            overview = "is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
            episodeNumber = 1,
            runtime = 65,
            stillPath = ""
        )
    )
}