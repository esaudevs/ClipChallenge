package com.esaudev.clipchallenge.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.esaudev.clipchallenge.R
import com.esaudev.clipchallenge.ui.theme.ClipChallengeTheme
import com.esaudev.clipchallenge.ui.theme.LocalSpacing

@Composable
fun PokemonAspectItem(
    modifier: Modifier = Modifier,
    @DrawableRes aspectIdRes: Int,
    aspectTitle: String,
    aspectValue: String,
    aspectContentDesc: String
) {
    val spacing = LocalSpacing.current

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = spacing.spaceSmall,
                vertical = spacing.spaceMedium
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(64.dp),
                painter = painterResource(id = aspectIdRes),
                contentDescription = aspectContentDesc,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            Text(
                text = aspectTitle,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Text(
                text = aspectValue,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun PokemonAspectItemPreview() {
    ClipChallengeTheme {
        Surface {
            PokemonAspectItem(
                aspectIdRes = R.drawable.ic_happiness,
                aspectTitle = "Base happiness",
                aspectValue = "50",
                aspectContentDesc = "Pokemon base happiness"
            )
        }
    }
}