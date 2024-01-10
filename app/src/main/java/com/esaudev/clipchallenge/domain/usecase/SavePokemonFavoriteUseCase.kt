package com.esaudev.clipchallenge.domain.usecase

import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.domain.repository.PokemonRepository
import com.esaudev.clipchallenge.ext.capitalizeByLocale
import java.time.Clock
import java.time.Instant
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class SavePokemonFavoriteUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val clock: Clock
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
                timeStamp = Instant.now(clock).toEpochMilli()
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