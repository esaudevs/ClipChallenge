package com.esaudev.clipchallenge.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import com.esaudev.clipchallenge.ui.components.PokemonAspectItem
import com.esaudev.clipchallenge.ui.theme.LocalSpacing

@Composable
fun PokemonDetailRoute(
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    onAbilitiesClick: (String) -> Unit,
    onEvolutionClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPokemonDetail()
    }

    PokemonDetailScreen(
        uiState = uiState,
        onAbilitiesClick = onAbilitiesClick,
        onEvolutionClick = onEvolutionClick,
        onBackClick = onBackClick
    )
}

@Composable
fun PokemonDetailScreen(
    uiState: PokemonDetailUiState,
    onAbilitiesClick: (String) -> Unit,
    onEvolutionClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    when (uiState) {
        is PokemonDetailUiState.PokemonDetail -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                PokemonDetailToolbar(
                    onBackClick = onBackClick
                )
                PokemonDetailContent(
                    pokemonDetail = uiState,
                    onAbilitiesClick = onAbilitiesClick,
                    onEvolutionClick = onEvolutionClick
                )
            }
        }

        else -> Unit
    }
}

@Composable
private fun PokemonDetailToolbar(
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
            text = stringResource(id = R.string.pokemon_detail__toolbar_title),
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
private fun PokemonDetailContent(
    pokemonDetail: PokemonDetailUiState.PokemonDetail,
    onAbilitiesClick: (String) -> Unit,
    onEvolutionClick: (Int) -> Unit
) {
    val spacing = LocalSpacing.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(spacing.spaceMedium)
    ) {
        item {
            Text(
                text = pokemonDetail.pokemonSpecies.pokemonName.capitalizeByLocale(),
                style = MaterialTheme.typography.h2
            )

            Spacer(modifier = Modifier.height(spacing.spaceSmall))

            val pokemonEggGroups = buildString {
                append(stringResource(id = R.string.pokemon_detail__egg_groups))
                append(" ")
                append(pokemonDetail.pokemonSpecies.eggGroups.joinToString(separator = ", ") { it.capitalizeByLocale() })
            }

            Text(
                text = pokemonEggGroups,
                style = MaterialTheme.typography.body1
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                PokemonAspectItem(
                    modifier = Modifier.weight(1f),
                    aspectIdRes = R.drawable.ic_happiness,
                    aspectTitle = stringResource(id = R.string.pokemon_detail__happiness_title),
                    aspectValue = pokemonDetail.pokemonSpecies.baseHappiness.toString(),
                    aspectContentDesc = stringResource(id = R.string.pokemon_detail__happiness_content_desc)
                )

                Spacer(modifier = Modifier.width(spacing.spaceSmall))

                PokemonAspectItem(
                    modifier = Modifier.weight(1f),
                    aspectIdRes = R.drawable.ic_capture_ratio,
                    aspectTitle = stringResource(id = R.string.pokemon_detail__capture_ratio_title),
                    aspectValue = pokemonDetail.pokemonSpecies.captureRate.toString(),
                    aspectContentDesc = stringResource(id = R.string.pokemon_detail__capture_ratio_content_desc)
                )
            }

            Spacer(modifier = Modifier.height(spacing.spaceLarge))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    // onAbilitiesClick(pokemonDetail.pokemonSpecies.pokemonName)
                },
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    modifier = Modifier.padding(spacing.spaceSmall),
                    text = stringResource(id = R.string.pokemon_detail__see_evolutions_button),
                    style = MaterialTheme.typography.body1
                )
            }

            Spacer(modifier = Modifier.height(spacing.spaceSmall))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAbilitiesClick(pokemonDetail.pokemonSpecies.pokemonName)
                },
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    modifier = Modifier.padding(spacing.spaceSmall),
                    text = stringResource(id = R.string.pokemon_detail__see_abilities_button),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}