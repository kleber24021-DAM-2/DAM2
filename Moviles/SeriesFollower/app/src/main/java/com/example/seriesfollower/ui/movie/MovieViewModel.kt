package com.example.seriesfollower.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesfollower.data.utils.NetworkResult
import com.example.seriesfollower.domain.model.movies.OwnMovie
import com.example.seriesfollower.domain.usecases.AddFavoriteItem
import com.example.seriesfollower.domain.usecases.CheckItemFavorite
import com.example.seriesfollower.domain.usecases.EraseFavoriteItem
import com.example.seriesfollower.domain.usecases.GetMovieById
import com.example.seriesfollower.ui.UserMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useGetMovieById: GetMovieById,
    private val checkItemFavorite: CheckItemFavorite,
    private val addFavoriteItem: AddFavoriteItem,
    private val eraseFavoriteItem: EraseFavoriteItem
) : ViewModel() {
    private val _movie = MutableLiveData<OwnMovie>()
    val movie: LiveData<OwnMovie> get() = _movie

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getMovieById(movieInt: Int) {
        viewModelScope.launch {

            when (val result = useGetMovieById.invoke(movieInt)) {
                is NetworkResult.Error -> _error.postValue(
                    result.message ?: UserMessages.UNEXPECTED_DB_ERROR
                )
                is NetworkResult.Success -> {
                    result.data.let {
                        if (it == null) {
                            _error.postValue(UserMessages.UNEXPECTED_DB_ERROR)
                        } else {
                            _isFavorite.postValue(checkItemFavorite.invoke(it))
                            _movie.postValue(it)
                        }
                    }
                }
                is NetworkResult.Loading -> _error.postValue(result.message ?: UserMessages.LOADING)
            }
        }
    }

    fun addFavoriteMovie() {
        viewModelScope.launch {
            movie.value?.let {
                addFavoriteItem.invoke(it)
                _isFavorite.postValue(true)
            }
        }
    }

    fun removeFavoriteMovie() {
        viewModelScope.launch {
            movie.value?.let {
                eraseFavoriteItem.invoke(it)
                _isFavorite.postValue(false)
            }
        }
    }
}
