package com.example.roomproject.data

import com.example.roomproject.data.model.SuperHeroEntity
import com.example.roomproject.data.model.SuperHeroWithComicsSeries
import javax.inject.Inject

class SuperHeroRepository @Inject constructor(private val dao: SuperHeroDao) {
    suspend fun createCompleteHero(toCreateHero: SuperHeroWithComicsSeries) =
        dao.createHeroWithComicsAndSeries(toCreateHero)

    suspend fun getCompleteHero(id: Int) = dao.getSuperHeroWithComicsSeries(id)
    suspend fun getSimpleSuperHeroes() = dao.getSimpleSuperHeroes()
    suspend fun updateHero(toUpdateHero: SuperHeroEntity) = dao.updateHeroe(toUpdateHero)
    suspend fun deleteHero(heroId: Int) = dao.deleteCompleteHero(heroId)

}