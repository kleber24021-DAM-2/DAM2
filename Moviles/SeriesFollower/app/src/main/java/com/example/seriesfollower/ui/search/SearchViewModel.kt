package com.example.seriesfollower.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesfollower.data.utils.NetworkResult
import com.example.seriesfollower.domain.usecases.GetResultsByQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getResultsByQuery: GetResultsByQuery
) : ViewModel() {
    private val _uiState: MutableStateFlow<SearchContract.State> by lazy {
        MutableStateFlow(SearchContract.State())
    }
    val uiState: StateFlow<SearchContract.State> = _uiState

    fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.GetSearchResults -> {
                viewModelScope.launch {
                    getResultsByQuery
                        .invoke(event.queryTerm, event.pageNum)
                        .catch(action = { cause ->
                            _uiState.update {
                                it.copy(error = cause.message)
                            }
                        })
                        .collect { result ->
                            when (result) {
                                is NetworkResult.Success -> {
                                    _uiState.update { state ->
                                        state.copy(
                                            queryInfo = result.data,
                                            isLoading = false
                                        )
                                    }
                                }
                                is NetworkResult.Loading -> {
                                    _uiState.update {
                                        it.copy(isLoading = true)
                                    }
                                }
                                is NetworkResult.Error -> {
                                    _uiState.update { it.copy(error = result.message) }
                                }
                            }
                        }
                }

            }
            is SearchContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(error = null)
                }
            }
        }
    }
}