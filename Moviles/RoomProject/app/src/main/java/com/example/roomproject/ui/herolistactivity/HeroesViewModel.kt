package com.example.roomproject.ui.herolistactivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomproject.domain.SuperHeroDisplay
import com.example.roomproject.ui.ConstantsUI
import com.example.roomproject.usecases.DeleteHero
import com.example.roomproject.usecases.GetHeroesWithoutComics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel @Inject constructor(
    private val getHeroesWithoutComics: GetHeroesWithoutComics,
    private val deleteHero: DeleteHero
) : ViewModel() {
    private val _heroes = MutableLiveData<List<SuperHeroDisplay>>()
    val heroes: LiveData<List<SuperHeroDisplay>> get() = _heroes

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    fun getHeroes() {
        viewModelScope.launch {
            try {
                _heroes.postValue(getHeroesWithoutComics.invoke())
                _error.postValue(true)
            } catch (exception: Exception) {
                Log.e(ConstantsUI.LOG_TAG_DB, exception.message.toString())
                _error.postValue(false)
            }
        }
    }

    fun deleteHeroes(toDeleteId: Int) {
        viewModelScope.launch {
            try {
                deleteHero.invoke(toDeleteId)
                _heroes.postValue(getHeroesWithoutComics.invoke())
                _error.postValue(true)
            } catch (exception: Exception) {
                Log.e(ConstantsUI.LOG_TAG_DB, exception.message.toString())
                _error.postValue(false)
            }
        }
    }

}