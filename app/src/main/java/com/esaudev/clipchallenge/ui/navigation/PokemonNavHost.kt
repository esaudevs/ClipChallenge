package com.esaudev.clipchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.esaudev.clipchallenge.ui.pokemondetail.navigation.navigateToPokemonDetail
import com.esaudev.clipchallenge.ui.pokemondetail.navigation.pokemonDetailScreen
import com.esaudev.clipchallenge.ui.pokemonlist.navigation.pokemonListScreen

@Composable
fun PokemonNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = "pokemon_list_route"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        pokemonListScreen(
            onPokemonClick = {
                navController.navigateToPokemonDetail(
                    pokemonId = it.id,
                    pokemonName = it.name
                )
            }
        )

        pokemonDetailScreen(
            onEvolutionClick = {
            },
            onAbilitiesClick = {
            },
            onBackClick = {
                navController.navigateUp()
            }
        )
    }
}