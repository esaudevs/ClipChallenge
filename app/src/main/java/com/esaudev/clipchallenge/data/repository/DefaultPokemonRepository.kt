package com.esaudev.clipchallenge.data.repository

import com.esaudev.clipchallenge.data.local.dao.PokemonNameDao
import com.esaudev.clipchallenge.data.local.model.toPokemonName
import com.esaudev.clipchallenge.data.remote.api.PokemonApi
import com.esaudev.clipchallenge.data.remote.model.toPokemonAbilities
import com.esaudev.clipchallenge.data.remote.model.toPokemonNameEntity
import com.esaudev.clipchallenge.data.remote.model.toPokemonNames
import com.esaudev.clipchallenge.data.remote.model.toPokemonSpecies
import com.esaudev.clipchallenge.domain.model.PokemonAbility
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
        try {
            val fetchResult = pokemonApi.fetchPokemonNames()
            if (fetchResult.isSuccessful) {
                val pokemonNameListEntity = fetchResult.body()?.results?.mapNotNull { pokemonListItemDto ->
                    pokemonListItemDto?.toPokemonNameEntity()
                }
                if (!pokemonNameListEntity.isNullOrEmpty()) {
                    pokemonNameDao.upsert(pokemonNameListEntity)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun fetchPokemonSpeciesByName(pokemonName: String): Result<PokemonSpecies> {
        return try {
            val fetchResult = pokemonApi.fetchPokemonSpeciesByName(pokemonName = pokemonName)
            if (fetchResult.isSuccessful) {
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
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun fetchPokemonAbilitiesByName(pokemonName: String): Result<List<PokemonAbility>> {
        return try {
            val fetchResult = pokemonApi.fetchPokemonAbilitiesByName(pokemonName = pokemonName)
            return if (fetchResult.isSuccessful) {
                val pokemonAbilities = fetchResult.body()?.toPokemonAbilities()?.filterNotNull()
                if (!pokemonAbilities.isNullOrEmpty()) {
                    Result.success(pokemonAbilities)
                } else {
                    Result.failure(Exception())
                }
            } else {
                Result.failure(Exception())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun fetchPokemonEvolutionChain(evolutionChain: Int): Result<List<PokemonName>> {
        return try {
            val fetchResult = pokemonApi.fetchPokemonEvolutionChainById(evolutionChain = evolutionChain)
            return if (fetchResult.isSuccessful) {
                val pokemonEvolutionChain = fetchResult.body()?.toPokemonNames()
                if (!pokemonEvolutionChain.isNullOrEmpty()) {
                    Result.success(pokemonEvolutionChain)
                } else {
                    Result.failure(Exception())
                }
            } else {
                Result.failure(Exception())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}