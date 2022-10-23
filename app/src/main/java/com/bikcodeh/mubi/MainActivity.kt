package com.bikcodeh.mubi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bikcodeh.mubi.presentation.navigation.MubiNavigation
import com.bikcodeh.mubi.presentation.theme.MubiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            MubiTheme {
                MubiNavigation(navController = navController)
            }
        }
    }
}