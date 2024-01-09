package com.esaudev.clipchallenge.domain.model

data class PokemonSpecies(
    val pokemonName: String,
    val baseHappiness: Int,
    val captureRate: Int,
    val eggGroups: List<String>,
    val evolutionChain: Int?
)