package com.esaudev.clipchallenge.data.remote.model

import com.esaudev.clipchallenge.domain.model.PokemonAbility
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonAbilitiesDto(
    @field:Json(name = "abilities") val abilities: List<PokemonAbilityDto>
)

fun PokemonAbilitiesDto.toPokemonAbilities(): List<PokemonAbility?> {
    return abilities.map {
        it.toPokemonAbility()
    }
}