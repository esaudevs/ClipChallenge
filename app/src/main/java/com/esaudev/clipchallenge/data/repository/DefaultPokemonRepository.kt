package com.esaudev.clipchallenge.data.repository

import com.esaudev.clipchallenge.data.local.dao.PokemonNameDao
import com.esaudev.clipchallenge.data.local.model.toPokemonName
import com.esaudev.clipchallenge.data.remote.api.PokemonApi
import com.esaudev.clipchallenge.data.remote.model.toPokemonNameEntity
import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultPokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonNameDao: PokemonNameDao
): PokemonRepository {
    override suspend fun getPokemonNames(): Flow<List<PokemonName>> {
        return pokemonNameDao.observeAll().map { localPokemonNameListEntity ->
            localPokemonNameListEntity.map { it.toPokemonName() }
        }
    }

    override suspend fun fetchPokemonNames() {
        val fetchResult = pokemonApi.fetchPokemonNames()
        if (fetchResult.isSuccessful) {
            val pokemonNameListEntity = fetchResult.body()!!.results?.mapNotNull {
                it?.toPokemonNameEntity()
            }
            if (!pokemonNameListEntity.isNullOrEmpty()) {
                pokemonNameDao.upsert(pokemonNameListEntity)
            }
        }
    }
}