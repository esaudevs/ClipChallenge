package com.esaudev.clipchallenge.data.remote.model

import com.esaudev.clipchallenge.domain.model.PokemonAbility
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonAbilityDto(
    @field:Json(name = "ability") val ability: PokemonAbilityNameDto
)

fun PokemonAbilityDto.toPokemonAbility(): PokemonAbility? {
    return ability.name?.let {
        PokemonAbility(
            name = it
        )
    }
}