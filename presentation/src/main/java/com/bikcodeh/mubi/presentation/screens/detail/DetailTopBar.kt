package com.bikcodeh.mubi.presentation.screens.detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.theme.COMMON_PADDING
import com.bikcodeh.mubi.presentation.theme.DEFAULT_ELEVATION
import com.bikcodeh.mubi.presentation.theme.GhostWhite
import com.bikcodeh.mubi.presentation.theme.backgroundColorTopBar

@Composable
fun DetailTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = GhostWhite,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = COMMON_PADDING),
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = GhostWhite
                )
            }
        },
        backgroundColor = MaterialTheme.colorScheme.backgroundColorTopBar,
        elevation = DEFAULT_ELEVATION
    )
}

@Preview(showBackground = true)
@Composable
fun DetailTopBarPreview() {
    DetailTopBar(title = "Breaking Bad", onBack = {})
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailTopBarPreviewDark() {
    DetailTopBar(title = "Breaking Bad", onBack = {})
}