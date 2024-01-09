package com.esaudev.clipchallenge.data.remote.model

import com.esaudev.clipchallenge.data.local.model.PokemonNameEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonListItemDto(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "url") val url: String?
)

fun PokemonListItemDto.toPokemonNameEntity(): PokemonNameEntity {
    checkNotNull(name)
    checkNotNull(url)
    return PokemonNameEntity(
        nameId = name,
        pokemonName = name
    )
}