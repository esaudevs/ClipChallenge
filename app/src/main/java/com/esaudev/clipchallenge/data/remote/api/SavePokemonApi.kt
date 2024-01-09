package com.esaudev.clipchallenge.data.remote.api

import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.delay

class SavePokemonApi @Inject constructor() {
    suspend fun saveFavorite(pokemonName: String): Boolean {
        delay(2000)
        return Random.nextBoolean()
    }
}