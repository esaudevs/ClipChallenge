package com.esaudev.clipchallenge.ui.abilities

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.clipchallenge.domain.model.PokemonAbility
import com.esaudev.clipchallenge.domain.repository.PokemonRepository
import com.esaudev.clipchallenge.ui.abilities.navigation.PokemonAbilitiesArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonAbilitiesViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonAbilitiesArgs: PokemonAbilitiesArgs = PokemonAbilitiesArgs(savedStateHandle)

    private val _uiState: MutableStateFlow<PokemonAbilitiesUiState> =
        MutableStateFlow(PokemonAbilitiesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getPokemonAbilities() {
        viewModelScope.launch {
            val pokemonAbilitiesResult = pokemonRepository.fetchPokemonAbilitiesByName(
                pokemonName = pokemonAbilitiesArgs.pokemonName
            )
            if (pokemonAbilitiesResult.isSuccess) {
                _uiState.value = PokemonAbilitiesUiState.PokemonAbilities(
                    pokemonAbilities = pokemonAbilitiesResult.getOrThrow()
                )
            }
        }
    }
}

sealed interface PokemonAbilitiesUiState {
    data object Loading : PokemonAbilitiesUiState

    data class PokemonAbilities(
        val pokemonAbilities: List<PokemonAbility>
    ) : PokemonAbilitiesUiState
}