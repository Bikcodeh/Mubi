package com.bikcodeh.mubi.presentation.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bikcodeh.mubi.presentation.R

@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    @StringRes imageDescriptionResId: Int
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_image),
        error = painterResource(R.drawable.ic_broken_image),
        contentDescription = stringResource(imageDescriptionResId),
        contentScale = ContentScale.Fit,
        modifier = modifier
    )
}