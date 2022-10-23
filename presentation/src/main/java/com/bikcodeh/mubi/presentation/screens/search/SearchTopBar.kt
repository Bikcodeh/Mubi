package com.bikcodeh.mubi.presentation.screens.search

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.components.MubiBackButton
import com.bikcodeh.mubi.presentation.theme.*

@Composable
fun SearchTopBar(
    text: String,
    onTextChange: (value: String) -> Unit,
    onBack: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(DEFAULT_HEIGHT_TOP_BAR)
            .background(color = MaterialTheme.colorScheme.backgroundColorTopBar)
    ) {
        val (back, search) = createRefs()
        MubiBackButton(
            onBack = onBack,
            modifier = Modifier
                .constrainAs(back) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(end = PADDING_32)
        )
        BasicTextField(
            textStyle = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.textColor
            ),
            maxLines = 1,
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.textColor),
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.backgroundColor,
                    MaterialTheme.shapes.extraSmall
                )
                .constrainAs(search) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    linkTo(back.end, parent.end, endMargin = COMMON_PADDING)
                    width = Dimension.fillToConstraints
                }
                .height(28.dp),
            decorationBox = { innerTextField ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = PADDING_8),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (text.isEmpty()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(id = R.string.search_placeholder),
                            color = CoolGrey,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    innerTextField()
                }

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchTopBarPreview() {
    SearchTopBar(text = "", onTextChange = {}, onBack = {})
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchTopBarPreviewDark() {
    SearchTopBar(text = "", onTextChange = {}, onBack = {})
}