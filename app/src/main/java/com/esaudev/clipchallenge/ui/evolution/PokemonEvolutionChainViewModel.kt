package com.esaudev.clipchallenge.ui.evolution

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.clipchallenge.domain.model.PokemonName
import com.esaudev.clipchallenge.domain.repository.PokemonRepository
import com.esaudev.clipchallenge.domain.usecase.SavePokemonFavoriteUseCase
import com.esaudev.clipchallenge.ui.evolution.navigation.PokemonEvolutionArgs
import com.esaudev.clipchallenge.ui.util.UiTopLevelEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonEvolutionChainViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val savePokemonFavoriteUseCase: SavePokemonFavoriteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonEvolutionArgs: PokemonEvolutionArgs = PokemonEvolutionArgs(savedStateHandle)

    private val _uiState: MutableStateFlow<PokemonEvolutionUiState> =
        MutableStateFlow(PokemonEvolutionUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiTopLevelEvent = Channel<UiTopLevelEvent>()
    val uiTopLevelEvent = _uiTopLevelEvent.receiveAsFlow()

    fun getPokemonEvolutionChain() {
        viewModelScope.launch {
            val evolutionChainResult = pokemonRepository.fetchPokemonEvolutionChain(
                evolutionChain = pokemonEvolutionArgs.evolutionChain
            )
            if (evolutionChainResult.isSuccess) {
                _uiState.value = PokemonEvolutionUiState.PokemonEvolutionList(
                    evolutions = evolutionChainResult.getOrThrow()
                )
            } else {
                _uiState.value = PokemonEvolutionUiState.Error
            }
        }
    }

    fun savePokemonFavorite(pokemonName: String) {
        viewModelScope.launch {
            savePokemonFavoriteUseCase.execute(pokemonName)
            _uiTopLevelEvent.send(UiTopLevelEvent.Success)
        }
    }
}

sealed interface PokemonEvolutionUiState {
    data object Loading : PokemonEvolutionUiState

    data class PokemonEvolutionList(
        val evolutions: List<PokemonName>
    ) : PokemonEvolutionUiState

    data object Error : PokemonEvolutionUiState
}