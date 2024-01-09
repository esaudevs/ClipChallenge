package com.esaudev.clipchallenge.ui.list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.ui.list.PokemonListRoute

const val pokemonListRoute = "pokemon_list_route"

fun NavController.navigateToPokemonList(navOptions: NavOptions? = null) {
    this.navigate(pokemonListRoute, navOptions)
}

fun NavGraphBuilder.pokemonListScreen(
    onPokemonClick: (PokemonName) -> Unit
) {
    composable(
        route = pokemonListRoute
    ) {
        PokemonListRoute(onPokemonClick = onPokemonClick)
    }
}