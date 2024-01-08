package com.esaudev.clipchallenge.domain.repository

import com.esaudev.clipchallenge.data.repository.DefaultPokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsPokemonRepository(
        pokemonRepositoryImpl: DefaultPokemonRepository
    ): PokemonRepository
}