package com.bikcodeh.mubi.presentation.screens.profile

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bikcodeh.mubi.presentation.screens.login.LoginViewModel

@Composable
fun ProfileScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onLogOut: () -> Unit,
    onBack: () -> Unit
) {
    ProfileContent(
        tvShows = emptyList(),
        onLogOut = {
            loginViewModel.saveLogin(isLoggedIn = false)
            onLogOut()
        },
        onBack = onBack
    )
}