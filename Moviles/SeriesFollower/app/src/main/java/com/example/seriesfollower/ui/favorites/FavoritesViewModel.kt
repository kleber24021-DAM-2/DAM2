package com.example.seriesfollower.ui.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesfollower.GeneralConstants
import com.example.seriesfollower.domain.model.favorite.FavoriteItem
import com.example.seriesfollower.domain.usecases.EraseFavoriteItem
import com.example.seriesfollower.domain.usecases.GetAllFavoriteMovies
import com.example.seriesfollower.domain.usecases.GetAllFavoriteSeries
import com.example.seriesfollower.ui.UserMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllFavoriteMovies: GetAllFavoriteMovies,
    private val getAllFavoriteSeries: GetAllFavoriteSeries,
    private val eraseFavoriteItem: EraseFavoriteItem,
) : ViewModel() {
    private val _favorites = MutableLiveData<List<FavoriteItem>>()
    val favorites: LiveData<List<FavoriteItem>> get() = _favorites

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

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
                val result: MutableList<FavoriteItem> =
                    getAllFavoriteMovies.invoke() as MutableList<FavoriteItem>
                result.addAll(getAllFavoriteSeries.invoke())
                _favorites.postValue(result)
            } catch (ex: Exception) {
                _error.postValue(UserMessages.UNEXPECTED_DB_ERROR)
                Log.e(GeneralConstants.LOG_TAG, ex.message, ex)
            }
        }
    }

    fun deleteFavorite(toDelete: FavoriteItem) {
        viewModelScope.launch {
            try {
                eraseFavoriteItem.invoke(toDelete)
            } catch (ex: Exception) {
                _error.postValue(UserMessages.UNEXPECTED_DB_ERROR)
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
                    _error.value = ex.message
                    Log.e(GeneralConstants.LOG_TAG, ex.message, ex)
                }

            }
        }
        getAllFavs()
        selectedList.clear()
    }

}