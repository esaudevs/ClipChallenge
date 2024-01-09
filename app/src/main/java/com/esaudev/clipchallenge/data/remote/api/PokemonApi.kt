package com.esaudev.clipchallenge.data.remote.api

import com.esaudev.clipchallenge.data.remote.model.PokemonListDto
import com.esaudev.clipchallenge.data.remote.model.PokemonSpeciesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("pokemon?limit=151")
    suspend fun fetchPokemonNames(): Response<PokemonListDto>

    @GET("pokemon-species/{pokemonId}")
    suspend fun fetchPokemonSpecies(
        @Path("pokemonId") pokemonId: String
    ): Response<PokemonSpeciesDto>
}