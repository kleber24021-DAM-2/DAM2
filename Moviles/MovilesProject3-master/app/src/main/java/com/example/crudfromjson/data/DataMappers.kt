package com.example.crudfromjson.data

import com.example.crudfromjson.domain.marvelmodels.Result
import com.example.crudfromjson.domain.ownmodels.SuperHero

fun Result.toHero(): SuperHero {
    val listOfComics = comics.items
        .map { it.name }.toMutableList()

    val listOfSeries = series.items
        .map { it.name }.toMutableList()

    return SuperHero(
        id,
        name,
        description,
        modified,
        thumbnail.path + "." + thumbnail.extension,
        listOfComics,
        listOfSeries
    )
}