package com.esaudev.clipchallenge.ui.pokemondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            _uiState.value = PokemonDetailUiState.PokemonDetail(
                id = pokemonDetailArgs.pokemonId,
                name = pokemonDetailArgs.pokemonName
            )
        }
    }
}

sealed interface PokemonDetailUiState {
    data object Loading : PokemonDetailUiState

    data class PokemonDetail(
        val id: String,
        val name: String
    ) : PokemonDetailUiState
}