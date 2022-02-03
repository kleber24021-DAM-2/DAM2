package com.example.seriesfollower.ui.series

import com.example.seriesfollower.domain.model.series.general.OwnSeries

interface SeriesContract {
    sealed class Event {
        data class GetSeries(val seriesId: Int) : SeriesContract.Event()
        object AddSeriesToFavorite : SeriesContract.Event()
        object RemoveSeriesFavorite : SeriesContract.Event()
        object ErrorMostrado : SeriesContract.Event()
    }

    data class State(
        val series: OwnSeries? = null,
        val isFavorite: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}