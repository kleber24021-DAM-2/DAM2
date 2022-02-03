package com.example.seriesfollower.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesfollower.data.utils.NetworkResult
import com.example.seriesfollower.domain.usecases.AddFavoriteItem
import com.example.seriesfollower.domain.usecases.CheckItemFavorite
import com.example.seriesfollower.domain.usecases.EraseFavoriteItem
import com.example.seriesfollower.domain.usecases.GetMovieById
import com.example.seriesfollower.ui.UserMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useGetMovieById: GetMovieById,
    private val checkItemFavorite: CheckItemFavorite,
    private val addFavoriteItem: AddFavoriteItem,
    private val eraseFavoriteItem: EraseFavoriteItem
) : ViewModel() {

    private val _uiState: MutableStateFlow<MoviesContract.State> by lazy {
        MutableStateFlow(MoviesContract.State())
    }
    val uiState: StateFlow<MoviesContract.State> = _uiState

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    fun handleEvent(event: MoviesContract.Event) {
        when (event) {
            is MoviesContract.Event.GetMovie -> {
                viewModelScope.launch {
                    useGetMovieById
                        .invoke(event.movieId)
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
                                    _uiState.update { state ->
                                        state.copy(
                                            movie = result.data,
                                            isLoading = false,
                                            isFavorite = result.data?.let {
                                                checkItemFavorite.invoke(it)
                                            } ?: false
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is MoviesContract.Event.AddMovieToFavorite -> {
                viewModelScope.launch {
                    _uiState.value.movie?.let {
                        addFavoriteItem.invoke(it)
                        _uiState.update { state ->
                            state.copy(isFavorite = true)
                        }
                    }
                }
            }
            is MoviesContract.Event.RemoveMovieFavorite -> {
                viewModelScope.launch {
                    _uiState.value.movie?.let {
                        eraseFavoriteItem.invoke(it)
                        _uiState.update { state ->
                            state.copy(isFavorite = false)
                        }
                    }
                }
            }
            is MoviesContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(error = null)
                }
            }
        }
    }
}
