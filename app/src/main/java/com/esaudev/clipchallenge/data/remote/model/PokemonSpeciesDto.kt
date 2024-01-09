package com.esaudev.clipchallenge.data.remote.model

import com.esaudev.clipchallenge.domain.model.PokemonSpecies
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonSpeciesDto(
    @field:Json(name = "base_happiness") val baseHappiness: Int,
    @field:Json(name = "capture_rate") val captureRate: Int,
    @field:Json(name = "egg_groups") val eggGroups: List<EggGroupDto>,
    @field:Json(name = "evolution_chain") val evolutionChain: EvolutionChainUrlDto
)

fun PokemonSpeciesDto.toPokemonSpecies(pokemonName: String): PokemonSpecies {
    return PokemonSpecies(
        pokemonName = pokemonName,
        baseHappiness = baseHappiness,
        captureRate = captureRate,
        eggGroups = eggGroups.map { it.name },
        evolutionChain = evolutionChain.url.getEvolutionChain()
    )
}

private fun String.getEvolutionChain(): Int? {
    val regex = """\b(\d+)\b""".toRegex()
    val matchResult = regex.find(this)

    return matchResult?.groupValues?.getOrNull(1)?.toInt()
}