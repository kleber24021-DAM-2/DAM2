package com.example.roomproject.ui.updateheroactivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomproject.domain.SuperHero
import com.example.roomproject.ui.ConstantsUI
import com.example.roomproject.usecases.EditHero
import com.example.roomproject.usecases.GetCompleteHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UpdateHeroViewModel @Inject constructor(
    private val editHero: EditHero,
    private val getHero: GetCompleteHero,
) : ViewModel() {
    private val _hero = MutableLiveData<SuperHero>()
    val hero: LiveData<SuperHero> get() = _hero

    private val _error = MutableLiveData<Boolean>()
    val error : LiveData<Boolean> get() = _error

    fun getCompleteHero(id: Int) {
        viewModelScope.launch {
            try {
                _hero.postValue(getHero.invoke(id))
                _error.postValue(true)
            }catch (exception : Exception){
                _error.postValue(false)
                Log.e(ConstantsUI.LOG_TAG_DB, exception.message.toString())
            }
        }
    }

    fun updateHero(toUpdate: SuperHero) {
        viewModelScope.launch {
            try {
                editHero.invoke(toUpdate)
                _error.postValue(true)
            }catch (exception : Exception){
                _error.postValue(false)
                Log.e(ConstantsUI.LOG_TAG_DB, exception.message.toString())
            }
        }
    }
}