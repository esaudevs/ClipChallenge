package com.esaudev.clipchallenge.domain.repository

import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.domain.model.PokemonSpecies
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonNames(): Flow<List<PokemonName>>

    suspend fun fetchPokemonNames()

    suspend fun fetchPokemonSpeciesByName(pokemonName: PokemonName): Result<PokemonSpecies>
}