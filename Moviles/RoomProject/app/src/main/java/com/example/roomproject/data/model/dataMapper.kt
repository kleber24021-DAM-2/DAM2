package com.example.roomproject.data.model

import com.example.roomproject.domain.Comic
import com.example.roomproject.domain.Series
import com.example.roomproject.domain.SuperHero
import com.example.roomproject.domain.SuperHeroDisplay

fun SuperHeroesWithComicsSeries.superHeroEntityToDomain() : SuperHero{
    return SuperHero(superHeroe.id, superHeroe.name, superHeroe.description,superHeroe.imageUrl, superHeroe.modifiedDate, comics?.map(ComicEntity::comicEntityToDomain), series?.map(SeriesEntity::seriesEntityToDomain))
}

fun SuperHero.superHeroDomainToEntity() : SuperHeroesWithComicsSeries{
    return SuperHeroesWithComicsSeries(SuperHeroEntity(id, name, description, imageUrl, modifiedDate), comicsList?.map{it.comicToEntity(id)}, seriesList?.map{it.seriesToEntity(id)})
}

fun SuperHeroEntity.superHeroEntityToDisplay() : SuperHeroDisplay{
    return SuperHeroDisplay(id, name, imageUrl)
}

fun ComicEntity.comicEntityToDomain() : Comic{
    return Comic(id, name, publishedDate)
}

fun Comic.comicToEntity(heroId:Int) : ComicEntity{
    return ComicEntity(id, name, publishedDate, heroId)
}

fun SeriesEntity.seriesEntityToDomain() : Series{
    return Series(id, name, publishDate)
}

fun Series.seriesToEntity(heroId:Int) : SeriesEntity{
    return SeriesEntity(id, name, publishedDate, heroId)
}