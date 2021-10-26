package com.example.crudfromjson.data

import com.example.crudfromjson.domain.marvelmodels.Data
import com.example.crudfromjson.domain.marvelmodels.MarvelObject
import com.example.crudfromjson.domain.ownmodels.Hero
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStream
import java.lang.reflect.Type

class MarvelRepository(file: InputStream? = null) {

    init {
        if (marvelList.size == 0) {
            val moshi = Moshi.Builder()
                .add(LocalTimeAdapter())
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val marvelObject1 = moshi.adapter(MarvelObject::class.java)
                .fromJson(file?.bufferedReader()?.readText())

            marvelObject1?.let { marvelObject -> marvelList.addAll(marvelObject.data.results.map { result -> result.toHero() }) }
        }
    }

    companion object {
        private val marvelList = mutableListOf<Hero>()
    }

    fun getList(): MutableList<Hero> {
        return marvelList
    }

    fun addHero(hero: Hero) {
        marvelList.add(hero)
    }
}