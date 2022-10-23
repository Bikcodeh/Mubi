package com.bikcodeh.mubi.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.screens.login.LoginViewModel
import com.bikcodeh.mubi.presentation.theme.BrilliantAzure
import com.bikcodeh.mubi.presentation.theme.GhostWhite
import com.bikcodeh.mubi.presentation.theme.PADDING_24
import com.bikcodeh.mubi.presentation.theme.VeryLightBlue
import kotlinx.coroutines.delay

@ExperimentalLifecycleComposeApi
@Composable
fun SplashScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val isLoggedIn by loginViewModel.isLoggedIn.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        loginViewModel.getIsLoggedIn()
    }

    val gradient = Brush.verticalGradient(
        listOf(
            BrilliantAzure,
            VeryLightBlue,
            VeryLightBlue
        )
    )
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = gradient
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.mubi_logo_white_with_ellipse),
            contentDescription = stringResource(
                id = R.string.mubi_logo_description
            ),
            modifier = Modifier.padding(end = PADDING_24)
        )
        Text(
            text = stringResource(id = R.string.mubi_title),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.ExtraBold,
            color = GhostWhite
        )
    }
    LaunchedEffect(key1 = true) {
        delay(800)
        if (isLoggedIn) navigateToHome() else navigateToLogin()
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navigateToHome = {}, navigateToLogin = {})
}