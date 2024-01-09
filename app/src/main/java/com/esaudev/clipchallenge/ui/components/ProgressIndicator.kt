package com.esaudev.clipchallenge.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.esaudev.clipchallenge.ui.theme.ClipChallengeTheme

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun ProgressIndicatorPreview() {
    ClipChallengeTheme {
        Surface {
            ProgressIndicator()
        }
    }
}