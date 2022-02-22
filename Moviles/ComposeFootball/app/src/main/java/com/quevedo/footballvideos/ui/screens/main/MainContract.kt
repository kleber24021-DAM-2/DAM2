package com.quevedo.footballvideos.ui.screens.main

import com.quevedo.footballvideos.domain.models.OwnFootballVideo

interface MainContract {
    sealed class Event {
        object GetData : Event()
        data class SelectVideo(val selectedVideo: OwnFootballVideo) : Event()
        object ErrorMostrado : Event()
    }

    data class State(
        val videosList: List<OwnFootballVideo>? = emptyList(),
        val selectedVideo : OwnFootballVideo? = null,
        val error: String? = null
    )
}