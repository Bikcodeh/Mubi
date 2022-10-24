package com.bikcodeh.mubi.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikcodeh.mubi.domain.model.TvShowType
import com.bikcodeh.mubi.domain.model.getAllTvShowsTypes
import com.bikcodeh.mubi.presentation.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MubiChips(
    tvShowsType: List<TvShowType> = getAllTvShowsTypes(),
    selectedCTvShowType: TvShowType,
    onSelectionChange: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = COMMON_PADDING,
                top = PADDING_VERTICAL_CHIPS,
                bottom = PADDING_VERTICAL_CHIPS
            )
    ) {
        items(tvShowsType) {
            MubiChipItem(
                tvShowType = it,
                isSelected = selectedCTvShowType == it,
                onSelectionChange = onSelectionChange
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MubiChipItem(
    tvShowType: TvShowType,
    isSelected: Boolean,
    onSelectionChange: (String) -> Unit
) {
    val color = if (isSelected) MaterialTheme.colorScheme.backgroundColorChipUnselected else Gray
    Chip(
        onClick = { onSelectionChange(tvShowType.tvName) },
        modifier = Modifier.padding(horizontal = 4.dp),
        colors = ChipDefaults.chipColors(
            backgroundColor = color
        )
    ) {
        Text(
            text = stringResource(id = tvShowType.title),
            color = if (isSelected) GhostWhite else OldLavender,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MubiChipsPreview() {
    MubiChips(
        tvShowsType = getAllTvShowsTypes(),
        selectedCTvShowType = TvShowType.POPULAR,
        onSelectionChange = {}
    )
}