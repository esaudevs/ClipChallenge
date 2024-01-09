package com.esaudev.clipchallenge.data.remote.api

import com.esaudev.clipchallenge.data.remote.model.PokemonAbilitiesDto
import com.esaudev.clipchallenge.data.remote.model.PokemonEvolutionChainDto
import com.esaudev.clipchallenge.data.remote.model.PokemonListDto
import com.esaudev.clipchallenge.data.remote.model.PokemonSpeciesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("pokemon?limit=151")
    suspend fun fetchPokemonNames(): Response<PokemonListDto>

    @GET("pokemon-species/{pokemonName}")
    suspend fun fetchPokemonSpeciesByName(
        @Path("pokemonName") pokemonName: String
    ): Response<PokemonSpeciesDto>

    @GET("pokemon/{pokemonName}")
    suspend fun fetchPokemonAbilitiesByName(
        @Path("pokemonName") pokemonName: String
    ): Response<PokemonAbilitiesDto>

    @GET("evolution-chain/{evolutionChain}")
    suspend fun fetchPokemonEvolutionChainById(
        @Path("evolutionChain") evolutionChain: Int
    ): Response<PokemonEvolutionChainDto>
}