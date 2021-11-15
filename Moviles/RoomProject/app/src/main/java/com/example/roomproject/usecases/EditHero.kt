package com.example.roomproject.usecases

import com.example.roomproject.data.SuperHeroRepository
import com.example.roomproject.data.model.domainToEntity
import com.example.roomproject.domain.SuperHero
import javax.inject.Inject

class EditHero @Inject constructor(private val repository: SuperHeroRepository) {
    suspend fun invoke(toUpdate:SuperHero) = repository.updateHero(toUpdate.domainToEntity())
}