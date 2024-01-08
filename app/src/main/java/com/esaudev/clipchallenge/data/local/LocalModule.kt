package com.esaudev.clipchallenge.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providesDefaultDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        PokemonDatabase::class.java,
        "pokemon_database"
    ).build()

    @Singleton
    @Provides
    fun providesPokemonNameDao(
        database: PokemonDatabase
    ) = database.pokemonNameDao()
}