package com.esaudev.clipchallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.esaudev.clipchallenge.data.local.model.PokemonNameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonNameDao {

    @Upsert
    suspend fun upsert(pokemonNameEntity: PokemonNameEntity)

    @Query("UPDATE pokemon_names SET pokemonName = :pokemonName, favoriteTimeStamp = :timeStamp WHERE nameId = :id")
    suspend fun updatePokemonNameById(pokemonName: String, id: String, timeStamp: Long?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsert(pokemonNameListEntity: List<PokemonNameEntity>)

    @Query("SELECT * FROM pokemon_names")
    fun observeAll(): Flow<List<PokemonNameEntity>>
}