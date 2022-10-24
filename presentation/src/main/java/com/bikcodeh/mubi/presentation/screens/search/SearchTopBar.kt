package com.bikcodeh.mubi.presentation.screens.search

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.components.MubiBackButton
import com.bikcodeh.mubi.presentation.theme.*
import com.bikcodeh.mubi.presentation.util.Constants.MAX_LENGTH

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    text: String,
    onTextChange: (value: String) -> Unit,
    onBack: () -> Unit,
    onSearch: (text: String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val displayClearButton = rememberSaveable { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(DEFAULT_HEIGHT_TOP_BAR)
            .padding(bottom = PADDING_4),
        color = MaterialTheme.colorScheme.backgroundColorTopBar,
        shadowElevation = DEFAULT_ELEVATION
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
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
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(text)
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                maxLines = 1,
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.textColor),
                value = text,
                onValueChange = { value ->
                    if (value.trim().count() < MAX_LENGTH) {
                        onTextChange(value)
                    }
                },
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
                    .height(SEARCH_HEIGHT)
                    .onFocusChanged {
                        displayClearButton.value = it.hasFocus
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        })
                    },
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
                        if (displayClearButton.value) {
                            IconButton(
                                onClick = {
                                    if (text.isNotEmpty()) {
                                        onTextChange(String())
                                    }
                                    if (text.isEmpty()) {
                                        focusManager.clearFocus()
                                        keyboardController?.hide()
                                    }
                                },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    Icons.Default.Clear,
                                    contentDescription = stringResource(id = R.string.clear_text),
                                    tint = MaterialTheme.colorScheme.textColor
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchTopBarPreview() {
    SearchTopBar(
        text = "",
        onTextChange = {},
        onBack = {},
        onSearch = {}
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchTopBarPreviewDark() {
    SearchTopBar(
        text = "",
        onTextChange = {},
        onBack = {},
        onSearch = {}
    )
}