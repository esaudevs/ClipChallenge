package com.esaudev.clipchallenge.domain.model

data class PokemonSpecies(
    val pokemonName: PokemonName,
    val baseHappiness: Int,
    val captureRate: Int,
    val eggGroups: List<String>
)