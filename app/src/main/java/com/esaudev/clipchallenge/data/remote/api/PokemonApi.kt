package com.esaudev.clipchallenge.data.remote.api

import com.esaudev.clipchallenge.data.remote.model.PokemonListDto
import retrofit2.Response
import retrofit2.http.GET

interface PokemonApi {

    @GET("pokemon?limit=151")
    suspend fun fetchPokemonNames(): Response<PokemonListDto>
}