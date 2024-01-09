package com.esaudev.clipchallenge.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.esaudev.clipchallenge.R
import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.ext.capitalizeByLocale
import com.esaudev.clipchallenge.ui.theme.ClipChallengeTheme

@Composable
fun PokemonEvolutionItem(
    modifier: Modifier = Modifier,
    pokemonName: PokemonName,
    onFavoriteClick: (String) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1.5f),
            text = pokemonName.name.capitalizeByLocale(),
            style = MaterialTheme.typography.body1
        )

        ProgressButton(
            modifier = Modifier.weight(1f),
            isLoading = false,
            text = stringResource(id = R.string.pokemon_evolution__save_favorite),
            onClick = {
                onFavoriteClick(pokemonName.name)
            }
        )
    }
}

@Preview
@Composable
fun PokemonEvolutionItemPreview() {
    ClipChallengeTheme {
        Surface {
            PokemonEvolutionItem(
                pokemonName = PokemonName(
                    id = "1",
                    name = "Charmander"
                ),
                onFavoriteClick = {}
            )
        }
    }
}