package com.esaudev.clipchallenge.ui.evolution.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.esaudev.clipchallenge.ui.detail.navigation.URL_CHARACTER_ENCODING
import com.esaudev.clipchallenge.ui.evolution.PokemonEvolutionRoute
import java.net.URLDecoder

const val POKEMON_EVOLUTION_CHAIN_ARG = "evolution_chain"

class PokemonEvolutionArgs(val evolutionChain: Int) {
    constructor(savedStateHandle: SavedStateHandle) :
        this(
            evolutionChain = URLDecoder.decode(
                checkNotNull(savedStateHandle[POKEMON_EVOLUTION_CHAIN_ARG]),
                URL_CHARACTER_ENCODING
            ).toInt()
        )
}

fun NavController.navigateToPokemonEvolution(
    evolutionChain: Int,
    navOptions: NavOptions? = null
) {
    val encodedEvolutionChain = Uri.encode(evolutionChain.toString())
    this.navigate("pokemon_evolution_route?evolution_chain=$encodedEvolutionChain", navOptions)
}

fun NavGraphBuilder.pokemonEvolutionScreen(
    onFavoriteClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    composable(
        route = "pokemon_evolution_route?evolution_chain={$POKEMON_EVOLUTION_CHAIN_ARG}",
        arguments = listOf(
            navArgument(POKEMON_EVOLUTION_CHAIN_ARG) { type = NavType.StringType }
        )
    ) {
        PokemonEvolutionRoute(
            onFavoriteClick = onFavoriteClick,
            onBackClick = onBackClick
        )
    }
}