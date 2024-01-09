package com.esaudev.clipchallenge.data.remote.model

import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.domain.model.PokemonSpecies
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonSpeciesDto(
    @field:Json(name = "base_happiness") val baseHappiness: Int,
    @field:Json(name = "capture_rate") val captureRate: Int,
    @field:Json(name = "egg_groups") val eggGroups: List<EggGroupDto>
)

fun PokemonSpeciesDto.toPokemonSpecies(pokemonName: PokemonName): PokemonSpecies {
    return PokemonSpecies(
        pokemonName = pokemonName,
        baseHappiness = baseHappiness,
        captureRate = captureRate,
        eggGroups = eggGroups.map { it.name }
    )
}
