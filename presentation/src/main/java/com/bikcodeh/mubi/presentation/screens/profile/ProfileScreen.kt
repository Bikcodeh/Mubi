package com.bikcodeh.mubi.presentation.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bikcodeh.mubi.presentation.screens.login.LoginViewModel

@ExperimentalLifecycleComposeApi
@Composable
fun ProfileScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onLogOut: () -> Unit,
    onBack: () -> Unit
) {
    val favorites by profileViewModel.favoritesTvShows.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        profileViewModel.getFavorites()
    }

    ProfileContent(
        tvShows = emptyList(),
        onLogOut = {
            loginViewModel.saveLogin(isLoggedIn = false)
            onLogOut()
        },
        onBack = onBack,
        favorites = favorites
    )
}