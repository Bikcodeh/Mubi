package com.bikcodeh.mubi.presentation.screens.home.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.screens.home.HomeDefaults.HOME_TITLE
import com.bikcodeh.mubi.presentation.theme.GhostWhite

@Composable
fun HomeTopBar(
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.home_title),
                color = GhostWhite,
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Default.Search, contentDescription = null, tint = GhostWhite)
            }
            IconButton(onClick = onProfileClick) {
                Icon(Icons.Default.AccountCircle, contentDescription = null, tint = GhostWhite)
            }
        }
    )
}

@Preview
@Composable
fun HomeTopBarPreview() {
    HomeTopBar(
        onProfileClick = {},
        onSearchClick = {}
    )
}