package com.esaudev.clipchallenge.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.esaudev.clipchallenge.domain.model.PokemonName

@Entity(tableName = "pokemon_names")
data class PokemonNameEntity(
    @PrimaryKey
    val nameId: String,
    val pokemonName: String,
    val url: String
)

fun PokemonNameEntity.toPokemonName(): PokemonName {
    return PokemonName(
        id = nameId,
        name = pokemonName
    )
}