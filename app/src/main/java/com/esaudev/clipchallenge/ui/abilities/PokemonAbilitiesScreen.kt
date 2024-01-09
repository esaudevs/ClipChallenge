package com.esaudev.clipchallenge.ui.abilities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.esaudev.clipchallenge.R
import com.esaudev.clipchallenge.ext.capitalizeByLocale
import com.esaudev.clipchallenge.ui.theme.LocalSpacing

@Composable
fun PokemonAbilitiesRoute(
    viewModel: PokemonAbilitiesViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPokemonAbilities()
    }

    PokemonAbilitiesScreen(
        uiState = uiState,
        onBackClick = onBackClick
    )
}

@Composable
fun PokemonAbilitiesScreen(
    uiState: PokemonAbilitiesUiState,
    onBackClick: () -> Unit
) {
    when (uiState) {
        is PokemonAbilitiesUiState.PokemonAbilities -> {
            Column {
                PokemonAbilitiesToolbar(
                    onBackClick = onBackClick
                )
                PokemonAbilitiesContent(
                    pokemonAbilities = uiState
                )
            }
        }

        else -> Unit
    }
}

@Composable
private fun PokemonAbilitiesContent(
    pokemonAbilities: PokemonAbilitiesUiState.PokemonAbilities
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.pokemon_abilities__header),
            style = MaterialTheme.typography.h2
        )

        Spacer(modifier = Modifier.height(spacing.spaceSmall))

        val abilitiesText = pokemonAbilities.pokemonAbilities.map {
            it.name
        }.joinToString(
            separator = ", "
        ) { it.capitalizeByLocale() }

        Text(
            text = abilitiesText,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun PokemonAbilitiesToolbar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    val spacing = LocalSpacing.current

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(
                    id = R.string.pokemon_detail__toolbar_back
                ),
                tint = MaterialTheme.colors.onPrimary
            )
        }

        Spacer(modifier = Modifier.width(spacing.spaceSmall))

        Text(
            text = stringResource(id = R.string.pokemon_abilities__toolbar_title),
            color = MaterialTheme.colors.onPrimary
        )
    }
}