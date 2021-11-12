package com.example.roomproject.data

import com.example.roomproject.data.SuperHeroDao

class SuperHeroRepository (private val dao: SuperHeroDao) {
    suspend fun getDisplayHeroes() = dao.getSuperHeroesWithComics()
    suspend fun getCompleteHero(id : Int) = dao.getSuperHeroWithComics(id)

}