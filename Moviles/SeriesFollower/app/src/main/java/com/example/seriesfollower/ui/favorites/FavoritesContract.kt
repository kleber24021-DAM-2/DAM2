package com.example.seriesfollower.ui.favorites

import com.example.seriesfollower.domain.model.favorite.FavoriteItem

interface FavoritesContract {
    sealed class Event{
        object GetFavorites : Event()
        data class BorrarFavorito(val toDelete : FavoriteItem) : Event()
        object ErrorMostrado: Event()
    }

    data class State(
        val favorites : List<FavoriteItem> = emptyList(),
        val error : String? = null
    )
}