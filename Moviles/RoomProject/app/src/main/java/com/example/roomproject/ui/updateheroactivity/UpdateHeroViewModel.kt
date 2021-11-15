package com.example.roomproject.ui.updateheroactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomproject.domain.SuperHero
import com.example.roomproject.usecases.EditHero
import com.example.roomproject.usecases.GetCompleteHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateHeroViewModel @Inject constructor(
    private val editHero: EditHero,
    private val getHero: GetCompleteHero,
) : ViewModel() {
    private val privateHero = MutableLiveData<SuperHero>()
    val hero: LiveData<SuperHero> get() = privateHero

    fun getCompleteHero(id: Int) {
        viewModelScope.launch {
            privateHero.postValue(getHero.invoke(id))
        }
    }

    fun updateHero(toUpdate: SuperHero) {
        viewModelScope.launch {
            editHero.invoke(toUpdate)
        }
    }
}