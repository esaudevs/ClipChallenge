package com.esaudev.clipchallenge.ui.detail.navigation

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.esaudev.clipchallenge.ui.detail.PokemonDetailRoute
import java.net.URLDecoder
import kotlin.text.Charsets.UTF_8

const val POKEMON_NAME_ARG = "pokemon_name"

private const val DEEP_LINK_URI_PATTERN =
    "https://poke.com/species?name={$POKEMON_NAME_ARG}"

val URL_CHARACTER_ENCODING: String = UTF_8.name()

class PokemonDetailArgs(val pokemonName: String) {
    constructor(savedStateHandle: SavedStateHandle) :
        this(
            pokemonName = URLDecoder.decode(
                checkNotNull(savedStateHandle[POKEMON_NAME_ARG]),
                URL_CHARACTER_ENCODING
            )
        )
}

fun NavController.navigateToPokemonDetail(
    pokemonName: String,
    navOptions: NavOptions? = null
) {
    val encodedPokemonName = Uri.encode(pokemonName)
    this.navigate("pokemon_detail_route?name=$encodedPokemonName", navOptions)
}

fun NavGraphBuilder.pokemonDetailScreen(
    onAbilitiesClick: (String) -> Unit,
    onEvolutionClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    composable(
        route = "pokemon_detail_route?name={$POKEMON_NAME_ARG}",
        deepLinks = listOf(
            navDeepLink {
                uriPattern = DEEP_LINK_URI_PATTERN
                action = Intent.ACTION_VIEW
            }
        ),
        arguments = listOf(
            navArgument(POKEMON_NAME_ARG) { type = NavType.StringType }
        )
    ) {
        PokemonDetailRoute(
            onAbilitiesClick = onAbilitiesClick,
            onEvolutionClick = onEvolutionClick,
            onBackClick = onBackClick
        )
    }
}