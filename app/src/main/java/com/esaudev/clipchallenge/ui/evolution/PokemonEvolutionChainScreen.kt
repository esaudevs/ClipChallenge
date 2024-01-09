package com.esaudev.clipchallenge.ui.evolution

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.esaudev.clipchallenge.R
import com.esaudev.clipchallenge.ui.components.EmptyPage
import com.esaudev.clipchallenge.ui.components.PokemonEvolutionItem
import com.esaudev.clipchallenge.ui.components.ProgressIndicator
import com.esaudev.clipchallenge.ui.theme.LocalSpacing
import com.esaudev.clipchallenge.ui.util.UiTopLevelEvent

@Composable
fun PokemonEvolutionRoute(
    viewModel: PokemonEvolutionChainViewModel = hiltViewModel(),
    onFavoriteResult: () -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getPokemonEvolutionChain()
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiTopLevelEvent.collect { event ->
            when (event) {
                is UiTopLevelEvent.Success -> {
                    onFavoriteResult()
                }
                else -> Unit
            }
        }
    }

    PokemonEvolutionChainScreen(
        uiState = uiState,
        onFavoriteClick = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        },
        onBackClick = onBackClick
    )
}

@Composable
fun PokemonEvolutionChainScreen(
    uiState: PokemonEvolutionUiState,
    onFavoriteClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    when (uiState) {
        is PokemonEvolutionUiState.PokemonEvolutionList -> {
            Column {
                PokemonEvolutionChainToolbar(
                    onBackClick = onBackClick
                )
                PokemonEvolutionChainContent(
                    pokemonEvolutionChain = uiState,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
        is PokemonEvolutionUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                PokemonEvolutionChainToolbar(
                    onBackClick = onBackClick
                )
                ProgressIndicator()
            }
        }

        is PokemonEvolutionUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                PokemonEvolutionChainToolbar(
                    onBackClick = onBackClick
                )
                EmptyPage(message = stringResource(id = R.string.pokemon_evolution__error_no_evolutions))
            }
        }
    }
}

@Composable
private fun PokemonEvolutionChainToolbar(
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
            text = stringResource(id = R.string.pokemon_evolution__toolbar_title),
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
private fun PokemonEvolutionChainContent(
    pokemonEvolutionChain: PokemonEvolutionUiState.PokemonEvolutionList,
    onFavoriteClick: (String) -> Unit
) {
    val spacing = LocalSpacing.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Text(
                text = stringResource(id = R.string.pokemon_evolution__evolution_title),
                style = MaterialTheme.typography.h2
            )

            Spacer(modifier = Modifier.height(spacing.spaceSmall))
        }

        items(pokemonEvolutionChain.evolutions, key = { it.name }) {
            Spacer(modifier = Modifier.height(spacing.spaceSmall))

            PokemonEvolutionItem(
                pokemonName = it,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}