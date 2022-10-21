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
val Gradient = Color(0xFF6243FF)
val GhostWhite = Color(0xFFFBFAFE)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val VeryLightBlue = Color(0xFF6243FF)
val BallBlue = Color(0xFF21BDCA)
val AntiFlashWhite = Color(0xFFF5F3F0)
val OldLavender = Color(0xFF6B6B83)
val GraniteGray = Color(0xFF656565)
val CharlestonGreen = Color(0xFF2A2A2A)
val ChineseBlack = Color(0xFF121212)

val ColorScheme.backgroundColor
    @Composable
    get() = if (!isSystemInDarkTheme()) AntiFlashWhite else ChineseBlack

val ColorScheme.backgroundCardColor
    @Composable
    get() = if (!isSystemInDarkTheme()) AntiFlashWhite else CharlestonGreen

val ColorScheme.textColor
    @Composable
    get() = if (!isSystemInDarkTheme()) OldLavender else Color.White