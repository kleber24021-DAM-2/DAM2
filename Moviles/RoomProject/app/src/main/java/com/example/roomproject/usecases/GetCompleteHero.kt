package com.example.roomproject.usecases

import com.example.roomproject.data.SuperHeroRepository
import com.example.roomproject.data.model.superHeroEntityToDomain

class GetCompleteHero (val repository: SuperHeroRepository){
    suspend fun invoke(id: Int) = repository.getCompleteHero(id).superHeroEntityToDomain()
}