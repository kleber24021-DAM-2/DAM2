package com.example.crudfromjson.data

import com.example.crudfromjson.domain.marvelmodels.Comic
import com.example.crudfromjson.domain.marvelmodels.MarvelHeroItem
import com.example.crudfromjson.domain.ownmodels.Hero
import com.example.crudfromjson.domain.ownmodels.OwnComic

fun MarvelHeroItem.toHero(): Hero {
    val listOfComics = comics.comics
        .map { it.toOwnComic() }.toMutableList()

    return Hero(
        name,
        description,
        modified,
        thumbnail.path,
        listOfComics
    )
}

fun Comic.toOwnComic() : OwnComic{
    return OwnComic(name)
}