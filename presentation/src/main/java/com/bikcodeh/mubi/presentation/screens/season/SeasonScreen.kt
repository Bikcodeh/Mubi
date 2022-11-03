package com.bikcodeh.mubi.presentation.screens.season

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bikcodeh.mubi.presentation.R
import com.bikcodeh.mubi.presentation.components.ErrorScreen
import com.bikcodeh.mubi.presentation.components.LoadingScreen
import com.bikcodeh.mubi.presentation.components.MubiBackButton
import com.bikcodeh.mubi.presentation.theme.COMMON_PADDING
import com.bikcodeh.mubi.presentation.theme.GhostWhite
import com.bikcodeh.mubi.presentation.theme.VeryLightBlue
import com.bikcodeh.mubi.presentation.theme.backgroundColorTopBar

@ExperimentalLifecycleComposeApi
@ExperimentalMaterial3Api
@Composable
fun SeasonScreen(
    onBack: () -> Unit,
    tvShowId: String,
    seasonNumber: Int,
    seasonViewModel: SeasonViewModel = hiltViewModel()
) {
    val seasonState by seasonViewModel.seasonUIState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = tvShowId) {
        seasonViewModel.getSeasonDetail(tvShowId, seasonNumber)
    }
    Scaffold(
        topBar = {
            androidx.compose.material.TopAppBar(
                title = {
                    androidx.compose.material.Text(
                        text = stringResource(id = R.string.season_number, seasonNumber),
                        color = GhostWhite,
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                elevation = COMMON_PADDING,
                navigationIcon = {
                    MubiBackButton(onBack = onBack)
                },
                backgroundColor = MaterialTheme.colorScheme.backgroundColorTopBar
            )

        }
    ) {
        if (seasonState.isLoading) {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }

        seasonState.error?.let { error ->
            ErrorScreen(
                modifier = Modifier.fillMaxSize(),
                messageId = error.errorMessage,
                onTryAgain = { seasonViewModel.getSeasonDetail(tvShowId, seasonNumber) },
                displayTryButton = error.displayTryAgainBtn
            )
        }
        SeasonContent(
            seasonState = seasonState, modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding() + COMMON_PADDING)
        )
    }

}
