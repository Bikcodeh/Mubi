package com.bikcodeh.mubi.presentation.screens.profile

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.components.GenericTopBar
import com.bikcodeh.mubi.presentation.components.MubiActionButton
import com.bikcodeh.mubi.presentation.components.TvShowItem
import com.bikcodeh.mubi.presentation.theme.*

@Composable
fun ProfileContent(
    onLogOut: () -> Unit,
    onBack: () -> Unit,
    favorites: List<TVShow>
) {
    val openDialog = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GenericTopBar(onBack = onBack, titleResId = R.string.profile_title, titleTop = null)
        ProfilePicture()

        TextFullCenter(
            textRedId = R.string.dummy_name,
            textStyle = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.textColor
        )
        TextFullCenter(
            textRedId = R.string.dummy_username,
            textStyle = MaterialTheme.typography.bodySmall,
            color = CoolGrey
        )

        FavoritesSection(favorites)

        MubiActionButton(
            onClick = { openDialog.value = true },
            buttonTextResId = R.string.log_out,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = COMMON_PADDING,
                    end = COMMON_PADDING,
                    top = PADDING_24,
                    bottom = PADDING_32
                )
        )
        if (openDialog.value) {
            AlertDialog(
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.backgroundColor,
                onDismissRequest = { openDialog.value = false },
                title = {
                    Text(
                        text = stringResource(id = R.string.are_u_sure_u_want_to_leave),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        openDialog.value = false
                    }) {
                        Text(
                            text = stringResource(id = R.string.stay_option).uppercase(),
                            color = VeryLightBlue,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        openDialog.value = false
                        onLogOut()
                    }) {
                        Text(
                            text = stringResource(id = R.string.leave_option).uppercase(),
                            color = Red,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun TextFullCenter(
    @StringRes textRedId: Int,
    textStyle: TextStyle,
    color: Color
) {
    Text(
        text = stringResource(id = textRedId),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = color,
        style = textStyle
    )
}

@Composable
fun ProfilePicture() {
    ConstraintLayout(modifier = Modifier.padding(vertical = COMMON_PADDING)) {
        val (ellipse, picture, edit) = createRefs()
        Image(painter = painterResource(id = R.drawable.ellipse), contentDescription = null,
            modifier = Modifier.constrainAs(ellipse) {

            })
        Image(
            painter = painterResource(id = R.drawable.avatar), contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(72.dp)
                .constrainAs(picture) {
                    start.linkTo(ellipse.start)
                    end.linkTo(ellipse.end)
                    top.linkTo(ellipse.top)
                    bottom.linkTo(ellipse.bottom)
                },
            contentScale = ContentScale.FillHeight
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp)
                .background(color = VeryLightBlue)
                .constrainAs(edit) {
                    end.linkTo(parent.end, margin = MEDIUM_PADDING)
                    bottom.linkTo(parent.bottom)
                }
                .clickable {

                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Edit,
                modifier = Modifier.size(12.dp),
                contentDescription = stringResource(id = R.string.edit_profile_picture),
                tint = GhostWhite
            )
        }
    }
}

@Composable
fun FavoritesSection(favorites: List<TVShow>) {
    Text(
        text = stringResource(id = R.string.my_favorites),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = PADDING_24,
                bottom = COMMON_PADDING,
                start = COMMON_PADDING,
                end = COMMON_PADDING
            ),
        color = MaterialTheme.colorScheme.textColor,
        style = MaterialTheme.typography.titleLarge
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = COMMON_PADDING),
        horizontalArrangement = Arrangement.spacedBy(COMMON_PADDING)
    ) {
        items(favorites) { tvShow ->
            TvShowItem(tvShow = tvShow, onClickItem = {})
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfilePicturePreview() {
    ProfilePicture()
}

@Composable
@Preview(showBackground = true)
fun ProfileContentPreview() {
    val tvShows = listOf(
        TVShow(
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
        TVShow(
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
        TVShow(
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
        TVShow(
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
        )
    )
    ProfileContent(
        onLogOut = {},
        onBack = {},
        favorites = tvShows
    )
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun ProfileContentPreviewDark() {
    val tvShows = listOf(
        TVShow(
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
        TVShow(
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
        TVShow(
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
        TVShow(
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
        )
    )
    ProfileContent(
        onLogOut = {},
        onBack = {},
        favorites = tvShows
    )
}