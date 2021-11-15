package com.example.roomproject.ui.addheroactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomproject.domain.SuperHero
import com.example.roomproject.usecases.InsertHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddHeroViewModel @Inject constructor(
    private val insertHero: InsertHero
) : ViewModel() {
    fun addHero(toAdd: SuperHero) {
        viewModelScope.launch {
            insertHero.invoke(toAdd)
        }
    }
}