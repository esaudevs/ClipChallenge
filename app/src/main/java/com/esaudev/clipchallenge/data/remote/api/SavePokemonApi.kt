package com.esaudev.clipchallenge.data.remote.api

import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class SavePokemonApi @Inject constructor() {
    suspend fun saveFavorite(pokemonName: String): Boolean {
        delay(2000)
        return Random.nextBoolean()
    }
}