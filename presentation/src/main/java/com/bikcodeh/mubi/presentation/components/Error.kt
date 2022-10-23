package com.bikcodeh.mubi.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.bikcodeh.mubi.presentation.theme.COMMON_PADDING
import com.bikcodeh.mubi.presentation.theme.ERROR_LOTTIE_SIZE
import com.bikcodeh.mubi.presentation.theme.FONT_SIZE_ERROR_MESSAGE
import com.bikcodeh.mubi.presentation.theme.textColor
import com.bikcodeh.mubi.domain.R as RD

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    @StringRes messageId: Int
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
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
    }
}

@Composable
fun handleError(error: Error): Int {
    return when (error) {
        Error.Connectivity -> RD.string.connectivity_error
        Error.InternetConnection -> RD.string.internet_error
        is Error.HttpException -> error.messageResId
        is Error.Unknown -> RD.string.unknown_error
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(messageId = R.string.home_title)
}