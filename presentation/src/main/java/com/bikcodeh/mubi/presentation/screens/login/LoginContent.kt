package com.bikcodeh.mubi.presentation.screens.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.components.MubiActionButton
import com.bikcodeh.mubi.presentation.theme.*

@Composable
fun LoginContent(
    onClickLogin: () -> Unit,
    navigateToHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.mubi_logo_with_ellipse),
            contentDescription = stringResource(
                id = R.string.mubi_logo_description
            )
        )
        Text(
            text = stringResource(id = R.string.mubi_title),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.ExtraBold,
            color = VeryLightBlue
        )

        Text(
            text = stringResource(id = R.string.sign_in),
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_24),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.textColor
        )
        MubiActionButton(
            onClick = {
                onClickLogin()
                navigateToHome()
            },
            buttonTextResId = R.string.log_in,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = COMMON_PADDING)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    LoginContent(onClickLogin = {}, navigateToHome = {})
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginContentPreviewDark() {
    LoginContent(onClickLogin = {}, navigateToHome = {})
}