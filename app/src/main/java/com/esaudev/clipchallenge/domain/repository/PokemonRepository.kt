package com.esaudev.clipchallenge.domain.repository

import com.esaudev.clipchallenge.domain.model.PokemonAbility
import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.domain.model.PokemonSpecies
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonNames(): Flow<List<PokemonName>>

    suspend fun fetchPokemonNames()

    suspend fun updatePokemon(pokemonName: String, pokemonId: String)

    suspend fun fetchPokemonSpeciesByName(pokemonName: String): Result<PokemonSpecies>

    suspend fun fetchPokemonAbilitiesByName(pokemonName: String): Result<List<PokemonAbility>>

    suspend fun fetchPokemonEvolutionChain(evolutionChain: Int): Result<List<PokemonName>>

    suspend fun savePokemonFavorite(pokemonName: String): Boolean
}