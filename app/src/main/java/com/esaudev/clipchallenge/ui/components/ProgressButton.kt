package com.esaudev.clipchallenge.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.esaudev.clipchallenge.ui.theme.ClipChallengeTheme

@Composable
fun ProgressButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick,
            enabled = !isLoading,
            shape = RoundedCornerShape(50)
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = text,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
@Preview(name = "Light mode", showBackground = true)
@Preview(name = "Dark mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ProgressButtonLoadingPreview() {
    ClipChallengeTheme {
        Surface {
            ProgressButton(
                isLoading = true,
                text = "Button text",
                onClick = {}
            )
        }
    }
}

@Composable
@Preview(name = "Light mode", showBackground = true)
@Preview(name = "Dark mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ProgressButtonNoLoadingPreview() {
    ClipChallengeTheme {
        Surface {
            ProgressButton(
                isLoading = false,
                text = "Button text",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}