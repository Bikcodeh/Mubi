package com.bikcodeh.mubi.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bikcodeh.mubi.presentation.theme.VeryLightBlue


@Composable
fun MubiActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes buttonTextResId: Int
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = VeryLightBlue
        )
    ) {
        Text(
            text = stringResource(id = buttonTextResId).uppercase(),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(4.dp)
        )
    }
}