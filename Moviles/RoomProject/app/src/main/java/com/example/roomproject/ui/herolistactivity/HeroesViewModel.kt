package com.example.roomproject.ui.herolistactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomproject.domain.SuperHeroDisplay
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
    private val privateHeroes = MutableLiveData<List<SuperHeroDisplay>>()
    val heroes: LiveData<List<SuperHeroDisplay>> get() = privateHeroes

    fun getHeroes() {
        viewModelScope.launch {
            privateHeroes.postValue(getHeroesWithoutComics.invoke())
        }
    }

    fun deleteHeroes(toDeleteId: Int) {
        viewModelScope.launch {
            deleteHero.invoke(toDeleteId)
            privateHeroes.postValue(getHeroesWithoutComics.invoke())
        }
    }

}