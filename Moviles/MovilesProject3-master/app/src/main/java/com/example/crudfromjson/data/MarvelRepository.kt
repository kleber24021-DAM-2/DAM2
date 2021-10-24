package com.example.crudfromjson.data

import com.example.crudfromjson.domain.marvelmodels.MarvelHeroItem
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStream
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MarvelRepository {

    constructor(file: InputStream?=null){
        if (marvelList.size == 0){
            val moshi = Moshi.Builder()
                .add(LocalTimeAdapter())
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val listOfCardsType: Type = Types.newParameterizedType(
                MutableList::class.java

            )
        }
    }

    companion object {
        private val marvelList = mutableListOf<MarvelHeroItem>()
    }


}