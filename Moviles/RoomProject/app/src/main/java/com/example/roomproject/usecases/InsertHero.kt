package com.example.roomproject.usecases

import com.example.roomproject.data.SuperHeroRepository
import com.example.roomproject.data.model.domainToSuperHeroWithComics
import com.example.roomproject.domain.SuperHero
import javax.inject.Inject

class InsertHero @Inject constructor(private val repository: SuperHeroRepository) {
    suspend fun invoke(toCreate: SuperHero) =
        repository.createCompleteHero(toCreate.domainToSuperHeroWithComics())
}