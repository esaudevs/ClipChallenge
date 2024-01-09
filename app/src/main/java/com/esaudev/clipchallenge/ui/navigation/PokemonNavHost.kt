package com.esaudev.clipchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.esaudev.clipchallenge.ui.abilities.navigation.navigateToPokemonAbilities
import com.esaudev.clipchallenge.ui.abilities.navigation.pokemonAbilitiesScreen
import com.esaudev.clipchallenge.ui.detail.navigation.navigateToPokemonDetail
import com.esaudev.clipchallenge.ui.detail.navigation.pokemonDetailScreen
import com.esaudev.clipchallenge.ui.evolution.navigation.navigateToPokemonEvolution
import com.esaudev.clipchallenge.ui.evolution.navigation.pokemonEvolutionScreen
import com.esaudev.clipchallenge.ui.list.navigation.pokemonListRoute
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
        modifier = modifier
    ) {
        pokemonListScreen(
            onPokemonClick = {
                navController.navigateToPokemonDetail(
                    pokemonName = it.id
                )
            }
        )

        pokemonDetailScreen(
            onEvolutionClick = {
                navController.navigateToPokemonEvolution(it)
            },
            onAbilitiesClick = {
                navController.navigateToPokemonAbilities(it)
            },
            onBackClick = {
                navController.navigateUp()
            }
        )

        pokemonAbilitiesScreen(
            onBackClick = {
                navController.navigateUp()
            }
        )

        pokemonEvolutionScreen(
            onBackClick = {
                navController.navigateUp()
            },
            onFavoriteResult = {
                navController.popBackStack(pokemonListRoute, false)
            }
        )
    }
}