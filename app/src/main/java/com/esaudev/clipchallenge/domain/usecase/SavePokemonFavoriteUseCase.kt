package com.esaudev.clipchallenge.domain.usecase

import com.esaudev.clipchallenge.domain.repository.PokemonRepository
import com.esaudev.clipchallenge.ext.capitalizeByLocale
import javax.inject.Inject

class SavePokemonFavoriteUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    suspend fun execute(pokemonName: String) {
        val favoriteSaved = pokemonRepository.savePokemonFavorite(
            pokemonName = pokemonName
        )

        val prefix = if (favoriteSaved) "S-" else "F-"

        val updatedPokemonName = prefix + pokemonName.capitalizeByLocale()

        pokemonRepository.updatePokemon(pokemonName = updatedPokemonName)
    }
}