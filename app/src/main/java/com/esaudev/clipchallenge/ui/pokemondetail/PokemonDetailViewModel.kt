package com.esaudev.clipchallenge.ui.pokemondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.clipchallenge.domain.model.PokemonSpecies
import com.esaudev.clipchallenge.domain.repository.PokemonRepository
import com.esaudev.clipchallenge.ui.pokemondetail.navigation.PokemonDetailArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonDetailArgs: PokemonDetailArgs = PokemonDetailArgs(savedStateHandle)

    private val _uiState: MutableStateFlow<PokemonDetailUiState> =
        MutableStateFlow(PokemonDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getPokemonDetail() {
        viewModelScope.launch {
            val pokemonSpeciesResult = pokemonRepository.fetchPokemonSpeciesByName(
                pokemonName = pokemonDetailArgs.pokemonName
            )

            if (pokemonSpeciesResult.isSuccess) {
                _uiState.value = PokemonDetailUiState.PokemonDetail(
                    pokemonSpecies = pokemonSpeciesResult.getOrThrow()
                )
            }
        }
    }
}

sealed interface PokemonDetailUiState {
    data object Loading : PokemonDetailUiState

    data class PokemonDetail(
        val pokemonSpecies: PokemonSpecies
    ) : PokemonDetailUiState
}