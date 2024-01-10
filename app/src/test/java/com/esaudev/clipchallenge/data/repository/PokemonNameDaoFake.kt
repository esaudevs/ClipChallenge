package com.esaudev.clipchallenge.data.repository

import com.esaudev.clipchallenge.data.local.dao.PokemonNameDao
import com.esaudev.clipchallenge.data.local.model.PokemonNameEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonNameDaoFake : PokemonNameDao {
    private val pokemonNames: MutableList<PokemonNameEntity> = mutableListOf()

    override suspend fun upsert(pokemonNameEntity: PokemonNameEntity) {
        pokemonNames.add(pokemonNameEntity)
    }

    override suspend fun upsert(pokemonNameListEntity: List<PokemonNameEntity>) {
        pokemonNames.addAll(pokemonNameListEntity)
    }

    override suspend fun updatePokemonNameById(pokemonName: String, id: String, timeStamp: Long?) {
        val existingPokemon = pokemonNames.find { it.nameId == id } ?: return

        val updatedPokemon = existingPokemon.copy(
            pokemonName = pokemonName,
            favoriteTimeStamp = timeStamp
        )

        pokemonNames.remove(existingPokemon)
        pokemonNames.add(updatedPokemon)
    }

    override fun observeAll(): Flow<List<PokemonNameEntity>> {
        return flow { emit(pokemonNames) }
    }
}