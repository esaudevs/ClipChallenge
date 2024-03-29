package com.esaudev.clipchallenge.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.esaudev.clipchallenge.R
import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.ui.components.EmptyPage
import com.esaudev.clipchallenge.ui.components.PokemonNameItem
import com.esaudev.clipchallenge.ui.components.ProgressIndicator

@Composable
fun PokemonListRoute(
    viewModel: PokemonListViewModel = hiltViewModel(),
    onPokemonClick: (PokemonName) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPokemonNames()
    }

    PokemonListScreen(
        uiState = uiState,
        onPokemonClick = onPokemonClick
    )
}

@Composable
fun PokemonListScreen(
    uiState: PokemonListUiState,
    onPokemonClick: (PokemonName) -> Unit
) {
    when (uiState) {
        is PokemonListUiState.PokemonListWithNames -> {
            PokemonListContent(
                uiState = uiState,
                onPokemonClick = onPokemonClick
            )
        }

        is PokemonListUiState.Loading -> {
            ProgressIndicator()
        }

        is PokemonListUiState.Empty -> {
            EmptyPage(message = stringResource(id = R.string.pokemon_list__empty_list))
        }
    }
}

@Composable
private fun PokemonListContent(
    uiState: PokemonListUiState.PokemonListWithNames,
    onPokemonClick: (PokemonName) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(uiState.pokemonNames, key = { it.id }) {
            PokemonNameItem(pokemonName = it, onClick = onPokemonClick)
        }

        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}