package com.esaudev.clipchallenge.ui.abilities.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.esaudev.clipchallenge.ui.abilities.PokemonAbilitiesRoute
import com.esaudev.clipchallenge.ui.detail.navigation.POKEMON_NAME_ARG
import com.esaudev.clipchallenge.ui.detail.navigation.URL_CHARACTER_ENCODING
import java.net.URLDecoder

class PokemonAbilitiesArgs(val pokemonName: String) {
    constructor(savedStateHandle: SavedStateHandle) :
        this(
            pokemonName = URLDecoder.decode(
                checkNotNull(savedStateHandle[POKEMON_NAME_ARG]),
                URL_CHARACTER_ENCODING
            )
        )
}

fun NavController.navigateToPokemonAbilities(
    pokemonName: String,
    navOptions: NavOptions? = null
) {
    val encodedPokemonName = Uri.encode(pokemonName)
    this.navigate("pokemon_abilities_route?name=$encodedPokemonName", navOptions)
}

fun NavGraphBuilder.pokemonAbilitiesScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "pokemon_abilities_route?name={$POKEMON_NAME_ARG}",
        arguments = listOf(
            navArgument(POKEMON_NAME_ARG) { type = NavType.StringType }
        )
    ) {
        PokemonAbilitiesRoute(
            onBackClick = onBackClick
        )
    }
}