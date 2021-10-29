package com.example.crudfromjson.data

import com.example.crudfromjson.domain.ownmodels.SuperHero


fun com.example.crudfromjson.domain.marvelmodels.Result.toHero(): SuperHero {
    val listOfComics = comics.items
        .map { it.name }.toMutableList()

    val listOfSeries = series.items
        .map { it.name }.toMutableList()

    return SuperHero(
        id,
        name,
        modified,
        thumbnail.path + "." + thumbnail.extension,
        listOfComics,
        listOfSeries
    )
}