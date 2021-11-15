package com.example.roomproject.data.model

import com.example.roomproject.domain.Comic
import com.example.roomproject.domain.Series
import com.example.roomproject.domain.SuperHero
import com.example.roomproject.domain.SuperHeroDisplay

fun SuperHeroWithComicsSeries.entityToDomain() : SuperHero{
    return SuperHero(
        superHeroComic.superHeroe.id,
        superHeroComic.superHeroe.name,
        superHeroComic.superHeroe.description,
        superHeroComic.superHeroe.imageUrl,
        superHeroComic.superHeroe.modifiedDate,
        superHeroComic.comics?.map(ComicEntity::entityToDomain),
        series?.map(SeriesEntity::entityToDomain))
}

fun SuperHero.domainToSuperHeroWithComics() : SuperHeroWithComicsSeries{
    return SuperHeroWithComicsSeries(
        SuperHeroesWithComics(
            SuperHeroEntity(
                id,
                name,
                description,
                imageUrl,
                modifiedDate),
            comicsList?.map{it.toEntity(id)})
        , seriesList?.map{it.toEntity(id)})
}

fun SuperHero.domainToEntity() : SuperHeroEntity{
    return SuperHeroEntity(
                id,
                name,
                description,
                imageUrl,
                modifiedDate)
}

fun SuperHeroEntity.entityToDomain() : SuperHeroDisplay{
    return SuperHeroDisplay(id, name, imageUrl)
}

fun ComicEntity.entityToDomain() : Comic{
    return Comic(id, name, publishedDate)
}

fun Comic.toEntity(heroId:Int) : ComicEntity{
    return ComicEntity(id, name, publishedDate, heroId)
}

fun SeriesEntity.entityToDomain() : Series{
    return Series(id, name, publishDate)
}

fun Series.toEntity(heroId:Int) : SeriesEntity{
    return SeriesEntity(id, name, publishedDate, heroId)
}