package com.esaudev.clipchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.esaudev.clipchallenge.ui.list.navigation.pokemonListScreen

@Composable
fun PokemonNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = "pokemon_list_route"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        pokemonListScreen(
            onPokemonClick = {

            }
        )
    }
}