package com.example.seriesfollower.ui.popular

import com.example.seriesfollower.domain.model.queryresult.QueryInfo

interface PopularContract {
    sealed class Event{
        data class PedirDatos(val numPage : Int) : Event()
        object ErrorMostrado : Event()
    }

    data class State(
        val favoriteItems: QueryInfo? = null,
        val isLoading : Boolean = false,
        val error : String? = null
    )
}