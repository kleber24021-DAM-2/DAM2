package com.example.seriesfollower.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesfollower.data.utils.NetworkResult
import com.example.seriesfollower.domain.usecases.GetTrendingResults
import com.example.seriesfollower.ui.UserMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val getTrendingResults: GetTrendingResults
) :
    ViewModel() {
    private val _uiState: MutableStateFlow<PopularContract.State> by lazy {
        MutableStateFlow(PopularContract.State())
    }
    val uiState: StateFlow<PopularContract.State> = _uiState

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    fun handleEvent(event: PopularContract.Event) {
        when (event) {
            is PopularContract.Event.PedirDatos -> {
                viewModelScope.launch {
                    getTrendingResults
                        .invoke(event.numPage)
                        .catch(action = { cause ->
                            _error.send(
                                cause.message ?: UserMessages.UNEXPECTED_DB_ERROR
                            )
                        })
                        .collect { result ->
                            when (result) {
                                is NetworkResult.Error -> {
                                    _uiState.update { it.copy(error = result.message) }
                                }
                                is NetworkResult.Loading -> {
                                    _uiState.update {
                                        it.copy(isLoading = true)
                                    }
                                }
                                is NetworkResult.Success -> {
                                    _uiState.update {
                                        it.copy(
                                            favoriteItems = result.data,
                                            isLoading = false
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is PopularContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(error = null)
                }
            }
        }
    }
}
