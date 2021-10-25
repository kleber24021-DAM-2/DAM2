package com.example.crudfromjson.data

import com.example.crudfromjson.domain.marvelmodels.Result
import com.example.crudfromjson.domain.ownmodels.Hero

fun Result.toHero(): Hero {
    val listOfComics = comics.items
        .map { it.name }.toMutableList()

    val listOfSeries = series.items
        .map { it.name }.toMutableList()

    return Hero(
        name,
        description,
        modified,
        thumbnail.path,
        listOfComics,
        listOfSeries
    )
}