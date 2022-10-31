package com.bikcodeh.mubi.presentation.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.components.LoadingScreen
import com.bikcodeh.mubi.presentation.components.MubiChips
import com.bikcodeh.mubi.presentation.components.TvShowItem
import com.bikcodeh.mubi.presentation.screens.home.HomeDefaults.COLUMNS_ITEM
import com.bikcodeh.mubi.presentation.screens.home.HomeDefaults.PADDING_ITEM
import com.bikcodeh.mubi.presentation.screens.home.HomeDefaults.SPACING_ITEM
import com.bikcodeh.mubi.presentation.theme.COMMON_PADDING
import com.bikcodeh.mubi.presentation.theme.VeryLightBlue
import com.bikcodeh.mubi.presentation.theme.backgroundColor
import com.bikcodeh.mubi.presentation.theme.textColor
import com.bikcodeh.mubi.presentation.util.ErrorLoadState
import com.bikcodeh.mubi.presentation.util.extension.items
import com.bikcodeh.mubi.presentation.util.extension.rememberCustomLazyGridState

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    tvShows: LazyPagingItems<TVShow>,
    onClickItem: (tvShow: TVShow) -> Unit,
    errorState: ErrorLoadState,
    selectedCTvShowType: TvShowType,
    onSelectionChange: (String) -> Unit,
    isLoading: Boolean
) {
    Column(modifier = modifier) {
        MubiChips(
            onSelectionChange = onSelectionChange,
            selectedCTvShowType = selectedCTvShowType
        )
        if (isLoading) {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(COLUMNS_ITEM),
                contentPadding = PaddingValues(PADDING_ITEM),
                horizontalArrangement = Arrangement.spacedBy(SPACING_ITEM),
                verticalArrangement = Arrangement.spacedBy(SPACING_ITEM),
                state = tvShows.rememberCustomLazyGridState()
            ) {
                items(
                    items = tvShows,
                    key = { tvShow -> tvShow.name }
                ) { tvShow: TVShow? ->
                    tvShow?.let {
                        TvShowItem(it, onClickItem = onClickItem)
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