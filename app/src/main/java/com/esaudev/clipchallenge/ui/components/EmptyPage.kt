package com.esaudev.clipchallenge.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.esaudev.clipchallenge.ui.theme.LocalSpacing

@Composable
fun EmptyPage(
    modifier: Modifier = Modifier,
    message: String
) {
    val spacing = LocalSpacing.current

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = spacing.spaceLarge)
                .align(Alignment.Center),
            text = message,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}