package com.example.seriesfollower.ui.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesfollower.GeneralConstants
import com.example.seriesfollower.domain.model.favorite.FavoriteItem
import com.example.seriesfollower.domain.usecases.EraseFavoriteItem
import com.example.seriesfollower.domain.usecases.GetAllFavoritesAsFlow
import com.example.seriesfollower.ui.UserMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllFavoritesAsFlow: GetAllFavoritesAsFlow,
    private val eraseFavoriteItem: EraseFavoriteItem,
) : ViewModel() {
    private val _uiState: MutableStateFlow<FavoritesContract.State> by lazy {
        MutableStateFlow(FavoritesContract.State())
    }
    val uiState: StateFlow<FavoritesContract.State> = _uiState

    private val selectedList = mutableListOf<FavoriteItem>()

    fun isFavSelected(item: FavoriteItem): Boolean {
        return selectedList.contains(item)
    }

    fun delFavSelected(item: FavoriteItem) {
        selectedList.remove(item)
    }

    fun addFavSelected(item: FavoriteItem) {
        selectedList.add(item)
    }

    fun emptySelectedList() {
        selectedList.clear()
    }

    fun noSelectedItems(): Boolean {
        return selectedList.isEmpty()
    }

    fun numSelectedItems(): Int = selectedList.size

    fun handleEvents(event: FavoritesContract.Event) {
        when (event) {
            is FavoritesContract.Event.GetFavorites -> {
                viewModelScope.launch {
                    getAllFavoritesAsFlow
                        .invoke()
                        .catch(action = { cause ->
                            _uiState.update {
                                it.copy(
                                    error = cause.message
                                )
                            }
                        })
                        .collect {list ->
                            _uiState.update{
                                it.copy(
                                    favorites = list
                                )
                            }
                        }
                }
            }
            is FavoritesContract.Event.BorrarFavorito -> {
                viewModelScope.launch {
                    eraseFavoriteItem
                        .invoke(event.toDelete)
                }
            }
            is FavoritesContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            }
        }
    }

    fun delAllSelectedItems() {
        selectedList.forEach {
            viewModelScope.launch {
                try {
                    eraseFavoriteItem.invoke(it)
                } catch (ex: Exception) {
                    _uiState.update {
                        it.copy(
                            error = ex.message ?: UserMessages.UNEXPECTED_DB_ERROR
                        )
                    }
                    Log.e(GeneralConstants.LOG_TAG, ex.message, ex)
                }
            }
        }
        selectedList.clear()
    }

}