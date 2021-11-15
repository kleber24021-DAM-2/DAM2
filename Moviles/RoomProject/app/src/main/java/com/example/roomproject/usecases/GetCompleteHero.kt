package com.example.roomproject.usecases

import com.example.roomproject.data.SuperHeroRepository
import com.example.roomproject.data.model.entityToDomain
import javax.inject.Inject

class GetCompleteHero @Inject constructor(private val repository: SuperHeroRepository){
    suspend fun invoke(id: Int) = repository.getCompleteHero(id).entityToDomain()
}