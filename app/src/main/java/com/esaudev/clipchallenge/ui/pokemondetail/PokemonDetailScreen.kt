package com.esaudev.clipchallenge.ui.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun PokemonDetailRoute(
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    onAbilitiesClick: (Int) -> Unit,
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
    onAbilitiesClick: (Int) -> Unit,
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
                    pokemonDetail = uiState
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
    pokemonDetail: PokemonDetailUiState.PokemonDetail
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = pokemonDetail.name.capitalizeByLocale(),
            style = MaterialTheme.typography.h2
        )

        Text(text = "Felicidad base ")
        Text(text = "Capture ratio")
        Text(text = "Egg groups, comma separated")
    }
}