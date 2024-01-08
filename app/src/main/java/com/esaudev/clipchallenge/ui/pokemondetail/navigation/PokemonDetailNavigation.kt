package com.esaudev.clipchallenge.ui.pokemondetail.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.esaudev.clipchallenge.ui.pokemondetail.PokemonDetailRoute
import java.net.URLDecoder
import kotlin.text.Charsets.UTF_8

const val pokemonIdArg = "pokemon_id"
const val pokemonNameArg = "pokemon_name"
private val URL_CHARACTER_ENCODING = UTF_8.name()

class PokemonDetailArgs(val pokemonId: String, val pokemonName: String) {
    constructor(savedStateHandle: SavedStateHandle) :
        this(
            pokemonId = URLDecoder.decode(checkNotNull(savedStateHandle[pokemonIdArg]), URL_CHARACTER_ENCODING),
            pokemonName = URLDecoder.decode(checkNotNull(savedStateHandle[pokemonNameArg]), URL_CHARACTER_ENCODING)
        )
}

fun NavController.navigateToPokemonDetail(
    pokemonId: Int,
    pokemonName: String,
    navOptions: NavOptions? = null
) {
    val encodedPokemonId = Uri.encode(pokemonId.toString())
    val encodedPokemonName = Uri.encode(pokemonName)
    this.navigate("pokemon_detail_route?pokemonId=$encodedPokemonId&pokemonName=$encodedPokemonName", navOptions)
}

fun NavGraphBuilder.pokemonDetailScreen(
    onAbilitiesClick: (Int) -> Unit,
    onEvolutionClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    composable(
        route = "pokemon_detail_route?pokemonId={$pokemonIdArg}&pokemonName={$pokemonNameArg}",
        arguments = listOf(
            navArgument(pokemonIdArg) { type = NavType.StringType },
            navArgument(pokemonNameArg) { type = NavType.StringType }
        )
    ) {
        PokemonDetailRoute(
            onAbilitiesClick = onAbilitiesClick,
            onEvolutionClick = onEvolutionClick,
            onBackClick = onBackClick
        )
    }
}