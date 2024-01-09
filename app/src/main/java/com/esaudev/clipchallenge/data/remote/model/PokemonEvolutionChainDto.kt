package com.esaudev.clipchallenge.data.remote.model

import com.esaudev.clipchallenge.domain.model.PokemonName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonEvolutionChainDto(
    @field:Json(name = "chain") val chain: EvolutionChainWrapperDto
)

fun PokemonEvolutionChainDto.toPokemonNames(): List<PokemonName> {
    return listOf(PokemonName(id = chain.species.name, name = chain.species.name)) + chain.evolvesTo
        .flatMap { evolutionChainDto ->
            val currentSpeciesName = evolutionChainDto.species.name
            val names: MutableList<PokemonName> = mutableListOf(
                PokemonName(
                    id = currentSpeciesName,
                    name = currentSpeciesName
                )
            )

            names.addAll(
                evolutionChainDto.evolvesTo.map {
                    PokemonName(
                        id = it.species.name,
                        name = it.species.name
                    )
                }
            )
            names
        }
}