package com.esaudev.clipchallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.esaudev.clipchallenge.data.local.model.PokemonNameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonNameDao {

    @Upsert
    suspend fun upsert(pokemonNameEntity: PokemonNameEntity)

    @Upsert
    suspend fun upsert(pokemonNameListEntity: List<PokemonNameEntity>)

    @Query("SELECT * FROM pokemon_names")
    fun observeAll(): Flow<List<PokemonNameEntity>>
}