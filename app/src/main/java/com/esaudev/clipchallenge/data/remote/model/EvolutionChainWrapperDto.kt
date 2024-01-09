package com.esaudev.clipchallenge.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EvolutionChainWrapperDto(
    @field:Json(name = "evolves_to") val evolvesTo: List<EvolutionChainDto>,
    @field:Json(name = "species") val species: EvolutionChainSpeciesDto
)