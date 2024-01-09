package com.esaudev.clipchallenge.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EvolutionChainDto(
    @field:Json(name = "species") val species: EvolutionChainSpeciesDto,
    @field:Json(name = "evolves_to") val evolvesTo: List<EvolutionChainDto>
)