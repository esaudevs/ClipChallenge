package com.esaudev.clipchallenge.domain.usecase

import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.domain.repository.PokemonRepository
import com.esaudev.clipchallenge.ext.capitalizeByLocale
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class SavePokemonFavoriteUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    suspend fun execute(pokemonName: String, pokemonId: String) {
        val favoriteSaved = pokemonRepository.savePokemonFavorite(
            pokemonName = pokemonName
        )

        val actualSavedPokemon = pokemonRepository.getPokemonNames()
            .first().map { it.name }

        val isPokemonSaved = actualSavedPokemon.contains(pokemonName)

        val prefix = if (favoriteSaved) "S-" else "F-"

        val updatedPokemonName = prefix + pokemonName.capitalizeByLocale()

        if (isPokemonSaved) {
            pokemonRepository.updatePokemon(
                pokemonName = updatedPokemonName,
                pokemonId = pokemonId,
                timeStamp = Date()
            )
        } else {
            pokemonRepository.savePokemon(
                pokemonName = PokemonName(
                    id = pokemonId,
                    name = updatedPokemonName
                )
            )
        }
    }
}