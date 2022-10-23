package com.bikcodeh.mubi.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val MediumBlue = Color(0xFF0013CA)
val Turquoise = Color(0xFF21BDCA)
val Red = Color(0xFFF65164)
val GhostWhite = Color(0xFFFBFAFE)

val BrilliantAzure = Color(0xFF31B5FE)
val VeryLightBlue = Color(0xFF6243FF)
val BallBlue = Color(0xFF21BDCA)
val AntiFlashWhite = Color(0xFFF5F3F0)
val OldLavender = Color(0xFF6B6B83)
val CharlestonGreen = Color(0xFF2A2A2A)
val ChineseBlack = Color(0xFF121212)
val Gray = Color(0xFFD5D8DB)

val ColorScheme.backgroundColor
    @Composable
    get() = if (!isSystemInDarkTheme()) AntiFlashWhite else ChineseBlack

val ColorScheme.backgroundCardColor
    @Composable
    get() = if (!isSystemInDarkTheme()) AntiFlashWhite else CharlestonGreen

val ColorScheme.textColor
    @Composable
    get() = if (!isSystemInDarkTheme()) OldLavender else Color.White

val ColorScheme.textColorLogo
    @Composable
    get() = if (!isSystemInDarkTheme()) GhostWhite else VeryLightBlue