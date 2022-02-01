package com.example.seriesfollower.ui.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesfollower.GeneralConstants
import com.example.seriesfollower.domain.model.favorite.FavoriteItem
import com.example.seriesfollower.domain.usecases.EraseFavoriteItem
import com.example.seriesfollower.domain.usecases.GetAllFavoriteMovies
import com.example.seriesfollower.domain.usecases.GetAllFavoriteSeries
import com.example.seriesfollower.ui.UserMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllFavoriteMovies: GetAllFavoriteMovies,
    private val getAllFavoriteSeries: GetAllFavoriteSeries,
    private val eraseFavoriteItem: EraseFavoriteItem,
) : ViewModel() {
    private var _favorites = MutableStateFlow<List<FavoriteItem>>(emptyList())
    val favorites: StateFlow<List<FavoriteItem>> get() = _favorites

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

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

    fun getAllFavs() {
        viewModelScope.launch {
            try {
                val resultList : MutableList<FavoriteItem> = mutableListOf()
                getAllFavoriteMovies.invoke().combine(getAllFavoriteSeries.invoke()){ a,b ->
                    resultList.addAll(a)
                    resultList.addAll(b)
                }
                _favorites.value = resultList

            } catch (ex: Exception) {
                _error.send(UserMessages.UNEXPECTED_DB_ERROR)
                Log.e(GeneralConstants.LOG_TAG, ex.message, ex)
            }
        }
    }


    fun deleteFavorite(toDelete: FavoriteItem) {
        viewModelScope.launch {
            try {
                eraseFavoriteItem.invoke(toDelete)
            } catch (ex: Exception) {
                _error.send(UserMessages.UNEXPECTED_DB_ERROR)
                Log.e(GeneralConstants.LOG_TAG, ex.message, ex)
            }
        }
        getAllFavs()
    }

    fun delAllSelectedItems() {
        selectedList.forEach {
            viewModelScope.launch {
                try {
                    eraseFavoriteItem.invoke(it)
                } catch (ex: Exception) {
                    _error.send(ex.message ?: UserMessages.UNEXPECTED_DB_ERROR)
                    Log.e(GeneralConstants.LOG_TAG, ex.message, ex)
                }
            }
        }
        getAllFavs()
        selectedList.clear()
    }

}