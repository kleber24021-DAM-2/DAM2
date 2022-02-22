package com.quevedo.footballvideos.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quevedo.footballvideos.data.models.NetworkResult
import com.quevedo.footballvideos.data.repositories.VideoRepository
import com.quevedo.footballvideos.utils.GeneralConsts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
    //Aquí me salto el usecase de forma consciente. En esta app no lo veo necesario
    //porque solo es una llamada y no hay que validar nada de nada, así que me salto toda esa capa
    private val videoRepository: VideoRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<MainContract.State> by lazy {
        MutableStateFlow(MainContract.State())
    }
    val uiState: StateFlow<MainContract.State> get() = _uiState

    private val _error = Channel<String>()
    val error: Channel<String> get() = _error

    fun handleEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.GetData -> {
                viewModelScope.launch {
                    videoRepository
                        .getAllVideos()
                        .catch(action = { cause ->
                            _error.send(
                                cause.message ?: GeneralConsts.UNEXPECTED_REST_ERROR
                            )
                        })
                        .collect { result ->
                            when (result) {
                                is NetworkResult.Error -> {
                                    _uiState.update { it.copy(error = result.message) }
                                }
                                is NetworkResult.Success -> {
                                    _uiState.update { state ->
                                        state.copy(
                                            videosList = result.data
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is MainContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(error = null)
                }
            }
        }
    }
}