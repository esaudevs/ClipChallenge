package com.esaudev.clipchallenge.ui.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<PokemonListUiState> =
        MutableStateFlow(PokemonListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getPokemonNames() {
        viewModelScope.launch {
            pokemonRepository.getPokemonNames().collectLatest { pokemonNames ->
                if (pokemonNames.isEmpty()) {
                    _uiState.value = PokemonListUiState.Empty
                } else {
                    _uiState.value = PokemonListUiState.PokemonListWithNames(
                        pokemonNames = pokemonNames
                    )
                }
            }
        }
    }
}

sealed interface PokemonListUiState {
    data object Loading : PokemonListUiState

    data class PokemonListWithNames(
        val pokemonNames: List<PokemonName>
    ) : PokemonListUiState

    data object Empty : PokemonListUiState
}