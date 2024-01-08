package com.esaudev.clipchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.esaudev.clipchallenge.data.local.dao.PokemonNameDao
import com.esaudev.clipchallenge.data.local.model.PokemonNameEntity

@Database(
    entities = [
        PokemonNameEntity::class
    ],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonNameDao(): PokemonNameDao
}