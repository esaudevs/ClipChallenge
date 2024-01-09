package com.esaudev.clipchallenge.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.ext.capitalizeByLocale

@Composable
fun PokemonNameItem(
    pokemonName: PokemonName,
    onClick: (PokemonName) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(pokemonName) }
    ) {
        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = pokemonName.name.capitalizeByLocale()
        )
    }
}

@Preview
@Composable
private fun PokemonNameItemPreview() {
    Surface {
        PokemonNameItem(
            pokemonName = PokemonName(
                name = "Bulbasaur"
            ),
            onClick = {}
        )
    }
}