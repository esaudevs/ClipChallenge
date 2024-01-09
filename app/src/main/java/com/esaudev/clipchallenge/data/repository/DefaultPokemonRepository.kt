package com.esaudev.clipchallenge.data.repository

import com.esaudev.clipchallenge.data.local.dao.PokemonNameDao
import com.esaudev.clipchallenge.data.local.model.toPokemonName
import com.esaudev.clipchallenge.data.remote.api.PokemonApi
import com.esaudev.clipchallenge.data.remote.model.toPokemonNameEntity
import com.esaudev.clipchallenge.data.remote.model.toPokemonSpecies
import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.domain.model.PokemonSpecies
import com.esaudev.clipchallenge.domain.repository.PokemonRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DefaultPokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonNameDao: PokemonNameDao
) : PokemonRepository {
    override suspend fun getPokemonNames(): Flow<List<PokemonName>> {
        val nothingCached = pokemonNameDao.observeAll().first().isEmpty()
        if (nothingCached) {
            fetchPokemonNames()
        }
        return pokemonNameDao.observeAll().map { localPokemonNameListEntity ->
            localPokemonNameListEntity.map { it.toPokemonName() }
        }
    }

    override suspend fun fetchPokemonNames() {
        val fetchResult = pokemonApi.fetchPokemonNames()
        if (fetchResult.isSuccessful) {
            val pokemonNameListEntity = fetchResult.body()?.results?.mapIndexedNotNull { index, pokemonListItemDto ->
                pokemonListItemDto?.toPokemonNameEntity(id = index + 1)
            }
            if (!pokemonNameListEntity.isNullOrEmpty()) {
                pokemonNameDao.upsert(pokemonNameListEntity)
            }
        }
    }

    override suspend fun fetchPokemonSpeciesByName(pokemonName: PokemonName): Result<PokemonSpecies> {
        val fetchResult = pokemonApi.fetchPokemonSpecies(pokemonId = pokemonName.id.toString())
        return if (fetchResult.isSuccessful) {
            val pokemonSpecies = fetchResult.body()?.toPokemonSpecies(
                pokemonName = pokemonName
            )
            if (pokemonSpecies != null) {
                Result.success(pokemonSpecies)
            } else {
                Result.failure(Exception())
            }
        } else {
            Result.failure(Exception())
        }
    }
}