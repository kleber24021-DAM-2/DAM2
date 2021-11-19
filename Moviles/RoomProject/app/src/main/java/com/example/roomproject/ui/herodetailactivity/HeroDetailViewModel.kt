package com.example.roomproject.ui.herodetailactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomproject.domain.SuperHero
import com.example.roomproject.usecases.GetCompleteHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(private val getCompleteHero: GetCompleteHero) :
    ViewModel() {

    private val _superHero = MutableLiveData<SuperHero>()
    val superHero: LiveData<SuperHero> get() = _superHero


    fun getCompleteHero(id: Int) {
        viewModelScope.launch {
            val superHero = getCompleteHero.invoke(id)
            _superHero.postValue(superHero)
        }
    }

}