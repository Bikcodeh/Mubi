package com.bikcodeh.mubi.presentation.screens.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {
    LoginContent(
        onClickLogin = { loginViewModel.saveLogin(isLoggedIn = true) },
        navigateToHome = navigateToHome
    )
}