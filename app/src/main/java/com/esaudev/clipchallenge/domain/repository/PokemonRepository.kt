package com.esaudev.clipchallenge.domain.repository

import com.esaudev.clipchallenge.domain.model.PokemonName
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonNames(): Flow<List<PokemonName>>

    suspend fun fetchPokemonNames()
}