package com.example.roomproject.usecases

import com.example.roomproject.data.SuperHeroRepository
import com.example.roomproject.domain.SuperHero
import javax.inject.Inject

class DeleteHero @Inject constructor(private val repository: SuperHeroRepository) {
    suspend fun invoke(superHero: SuperHero) = repository.deleteHero(superHero.id)
    suspend fun invoke(id : Int) = repository.deleteHero(id)
}