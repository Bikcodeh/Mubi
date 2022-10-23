package com.bikcodeh.mubi.presentation.screens.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

@ExperimentalMaterial3Api
@Composable
fun SearchScreen(onBack: () -> Unit) {

    val searchText = rememberSaveable { mutableStateOf("") }

    SearchContent(
        text = searchText.value,
        onTextChange = { searchText.value = it },
        onBack = onBack
    )
}