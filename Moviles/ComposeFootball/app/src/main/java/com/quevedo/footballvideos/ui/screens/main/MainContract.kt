package com.quevedo.footballvideos.ui.screens.main

import com.quevedo.footballvideos.domain.models.OwnFootballVideo

interface MainContract {
    sealed class Event{
        object GetData : Event()
        object ErrorMostrado : Event()
    }

    data class State(
        val videosList : List<OwnFootballVideo>? = emptyList(),
        val error : String? = null
    )
}