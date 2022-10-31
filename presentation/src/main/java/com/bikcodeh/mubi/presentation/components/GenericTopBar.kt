package com.bikcodeh.mubi.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.theme.*

@Composable
fun GenericTopBar(
    onBack: () -> Unit,
    @StringRes titleResId: Int?,
    titleTop: String?
) {
    val titleTopBar = if (titleResId != null) {
        stringResource(id = titleResId)
    } else {
        titleTop
    }
    Surface(
        color = VeryLightBlue,
        modifier = Modifier
            .fillMaxWidth()
            .height(DEFAULT_HEIGHT_TOP_BAR)
            .shadow(90.dp)
            .zIndex(1f)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (back, title) = createRefs()
            MubiBackButton(
                onBack = onBack,
                modifier = Modifier.constrainAs(back) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )
            Text(
                text = titleTopBar ?: String(),
                color = GhostWhite,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(back.end, margin = PADDING_32)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
        }
    }
}

@Preview
@Composable
fun GenericTopBarPreview() {
    GenericTopBar(
        onBack = {},
        titleResId = R.string.mubi_title,
        titleTop = null
    )
}