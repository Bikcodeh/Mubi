package com.bikcodeh.mubi.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.*
import com.bikcodeh.mubi.domain.common.Error
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.theme.*
import com.bikcodeh.mubi.domain.R as RD

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    @StringRes messageId: Int,
    onTryAgain: () -> Unit,
    displayTryButton: Boolean
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.backgroundColor)
            .padding(COMMON_PADDING),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition,
            progress = { progress },
            modifier = Modifier
                .size(ERROR_LOTTIE_SIZE)
        )
        Text(
            text = stringResource(id = messageId),
            fontSize = FONT_SIZE_ERROR_MESSAGE,
            color = MaterialTheme.colorScheme.textColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        if (displayTryButton) {
            MubiActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = COMMON_PADDING),
                onClick = onTryAgain,
                buttonTextResId = R.string.try_again
            )
        }
    }
}

@Composable
fun handleError(error: Error): Int {
    return when (error) {
        Error.Connectivity -> RD.string.connectivity_error
        Error.InternetConnection -> RD.string.internet_error
        is Error.HttpException -> error.messageResId
        is Error.Unknown -> RD.string.unknown_error
        is Error.NotFoundTvShow -> error.messageResId
    }
}

@Preview(heightDp = 600)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        messageId = R.string.home_title,
        onTryAgain = {},
        displayTryButton = true
    )
}

@Preview(heightDp = 600, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ErrorScreenPreviewDark() {
    ErrorScreen(
        messageId = R.string.home_title,
        onTryAgain = {},
        displayTryButton = true
    )
}