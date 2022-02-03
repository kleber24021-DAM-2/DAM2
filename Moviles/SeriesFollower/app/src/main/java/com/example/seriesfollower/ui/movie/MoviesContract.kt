package com.example.seriesfollower.ui.movie

import com.example.seriesfollower.domain.model.movies.OwnMovie

interface MoviesContract {
    sealed class Event{
        data class GetMovie(val movieId : Int) : Event()
        object AddMovieToFavorite : Event()
        object RemoveMovieFavorite : Event()
        object ErrorMostrado : Event()
    }

    data class State(
        val movie: OwnMovie? = null,
        val isFavorite: Boolean = false,
        val isLoading : Boolean = false,
        val error : String? = null
    )
}