package com.example.seriesfollower.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesfollower.data.utils.NetworkResult
import com.example.seriesfollower.domain.model.series.episode.OwnEpisode
import com.example.seriesfollower.domain.usecases.*
import com.example.seriesfollower.ui.UserMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val getSeriesById: GetSeriesById,
    private val checkItemFavorite: CheckItemFavorite,
    private val addFavoriteItem: AddFavoriteItem,
    private val eraseFavoriteItem: EraseFavoriteItem,
    private val getAllEpisodesOfSeason: GetAllEpisodesOfSeason
) : ViewModel() {
    private val _uiState: MutableStateFlow<SeriesContract.State> by lazy {
        MutableStateFlow(SeriesContract.State())
    }
    val uiState: StateFlow<SeriesContract.State> = _uiState

    fun handleEvent(event: SeriesContract.Event) {
        when (event) {
            is SeriesContract.Event.GetSeries -> {
                viewModelScope.launch {
                    getSeriesById
                        .invoke(event.seriesId)
                        .catch(action = { cause ->
                            _uiState.update {
                                it.copy(
                                    error = cause.message ?: UserMessages.UNEXPECTED_DB_ERROR
                                )
                            }
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
                                            series = result.data,
                                            isLoading = false,
                                            isFavorite = result.data?.let {
                                                checkItemFavorite.invoke(it)
                                            } ?: false
                                        )
                                    }
                                    handleEvent(SeriesContract.Event.GetSeriesEpisode)
                                }
                            }
                        }
                }
            }
            is SeriesContract.Event.GetSeriesEpisode -> {
                viewModelScope.launch {
                    _uiState.value.series?.let { ownSeries ->
                        for (season in ownSeries.listOfSeasons){
                            getAllEpisodesOfSeason.invoke(ownSeries.id, season.seasonNumber)
                                .catch(action = { cause ->
                                    _uiState.update {
                                        it.copy(
                                            error = cause.message ?: UserMessages.UNEXPECTED_DB_ERROR
                                        )
                                    }
                                })
                                .collect { result ->
                                    when (result) {
                                        is NetworkResult.Success -> {
                                            _uiState.update { state ->
                                                val tempList = mutableListOf<OwnEpisode>()
                                                result.data?.forEach { tempList.add(it) }
                                                state.seriesEpisode.forEach { tempList.add(it) }
                                                state.copy(
                                                    seriesEpisode = tempList,
                                                    isLoading = false
                                                )
                                            }
                                        }
                                        is NetworkResult.Loading -> {
                                            _uiState.update {
                                                it.copy(
                                                    isLoading = true
                                                )
                                            }
                                        }
                                        is NetworkResult.Error -> {
                                            _uiState.update {
                                                it.copy(
                                                    error = result.message
                                                        ?: UserMessages.UNEXPECTED_DB_ERROR
                                                )
                                            }
                                        }
                                    }
                                }
                        }
                    }
                }
            }
            is SeriesContract.Event.AddSeriesToFavorite -> {
                viewModelScope.launch {
                    _uiState.value.series?.let {
                        addFavoriteItem.invoke(it)
                    }
                    _uiState.update { state ->
                        state.copy(isFavorite = true)
                    }
                }
            }
            is SeriesContract.Event.RemoveSeriesFavorite -> {
                viewModelScope.launch {
                    _uiState.value.series?.let {
                        eraseFavoriteItem.invoke(it)
                        _uiState.update { state ->
                            state.copy(isFavorite = false)
                        }
                    }
                }
            }
            is SeriesContract.Event.ErrorMostrado -> {
                _uiState.update { state ->
                    state.copy(error = null)
                }
            }
        }
    }
}