package com.bikcodeh.mubi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bikcodeh.mubi.domain.usecase.GetTvShowsUC
import com.bikcodeh.mubi.presentation.screens.home.HomeScreen
import com.bikcodeh.mubi.presentation.theme.MubiTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getTvShowsUC: GetTvShowsUC

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MubiTheme {
                HomeScreen(onClickItem = {})
            }
        }
    }
}