package com.example.roomproject.ui.addheroactivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomproject.domain.SuperHero
import com.example.roomproject.ui.ConstantsUI
import com.example.roomproject.usecases.InsertHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddHeroViewModel @Inject constructor(
    private val insertHero: InsertHero
) : ViewModel() {

    private val _response = MutableLiveData<Boolean>()
    val response: LiveData<Boolean> get() = _response

    fun addHero(toAdd: SuperHero) {
        viewModelScope.launch {
            try {
                insertHero.invoke(toAdd)
                _response.postValue(true)
            }catch (e: Exception){
                Log.e(ConstantsUI.LOG_TAG_DB, e.message.toString())
                _response.postValue(false)
            }
        }
    }
}