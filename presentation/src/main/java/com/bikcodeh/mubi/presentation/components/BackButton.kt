package com.bikcodeh.mubi.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.theme.GhostWhite

@Composable
fun MubiBackButton(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    IconButton(
        onClick = onBack,
        modifier = modifier
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            tint = GhostWhite
        )
    }
}