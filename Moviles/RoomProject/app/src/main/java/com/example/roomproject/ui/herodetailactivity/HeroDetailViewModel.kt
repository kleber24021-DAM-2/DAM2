package com.example.roomproject.ui.herodetailactivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomproject.domain.SuperHero
import com.example.roomproject.ui.ConstantsUI
import com.example.roomproject.usecases.GetCompleteHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(private val getCompleteHero: GetCompleteHero) :
    ViewModel() {

    private val _superHero = MutableLiveData<SuperHero>()
    val superHero: LiveData<SuperHero> get() = _superHero

    private val _error = MutableLiveData<Boolean>()
    val error : LiveData<Boolean> get() = _error

    fun getCompleteHero(id: Int) {
        viewModelScope.launch {
            try {
                val superHero = getCompleteHero.invoke(id)
                _superHero.postValue(superHero)
                _error.postValue(true)
            }catch (exception : Exception){
                Log.e(ConstantsUI.LOG_TAG_DB, exception.message.toString())
                _error.postValue(false)
            }
        }
    }

}