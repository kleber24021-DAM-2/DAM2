package com.example.seriesfollower.ui.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesfollower.data.utils.NetworkResult
import com.example.seriesfollower.domain.model.series.general.OwnSeries
import com.example.seriesfollower.domain.usecases.AddFavoriteItem
import com.example.seriesfollower.domain.usecases.CheckItemFavorite
import com.example.seriesfollower.domain.usecases.EraseFavoriteItem
import com.example.seriesfollower.domain.usecases.GetSeriesById
import com.example.seriesfollower.ui.UserMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val getSeriesById: GetSeriesById,
    private val checkItemFavorite: CheckItemFavorite,
    private val addFavoriteItem: AddFavoriteItem,
    private val eraseFavoriteItem: EraseFavoriteItem
) : ViewModel() {
    private val _series = MutableLiveData<OwnSeries>()
    val series: LiveData<OwnSeries> get() = _series

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getSeriesById(seriesId: Int) {
        viewModelScope.launch {
            when (val result = getSeriesById.invoke(seriesId)) {
                is NetworkResult.Error -> _error.postValue(
                    result.message ?: UserMessages.UNEXPECTED_DB_ERROR
                )
                is NetworkResult.Success -> {
                    result.data?.let {
                        _isFavorite.postValue(checkItemFavorite.invoke(it))
                        _series.postValue(it)

                    }
                }
                is NetworkResult.Loading -> _error.postValue(result.message ?: UserMessages.LOADING)
            }
        }
    }

    fun addFavoriteSeries() {
        viewModelScope.launch {
            series.value?.let {
                addFavoriteItem.invoke(it)
                _isFavorite.postValue(true)
            }
        }
    }

    fun removeFavoriteSeries() {
        viewModelScope.launch {
            series.value?.let {
                eraseFavoriteItem.invoke(it)
                _isFavorite.postValue(false)
            }
        }
    }
}