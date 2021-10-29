package com.example.crudfromjson.data

import com.example.crudfromjson.domain.marvelmodels.MarvelObject
import com.example.crudfromjson.domain.ownmodels.SuperHero
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStream

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

    private companion object {
        private val marvelList = mutableListOf<SuperHero>()
    }

    fun getList(): MutableList<SuperHero> {
        return marvelList
    }

    fun deleteHero(toDelete: SuperHero) {
        marvelList.remove(toDelete)
    }

    fun addHero(index: Int, toAdd: SuperHero) {
        marvelList.add(index, toAdd)
    }

}