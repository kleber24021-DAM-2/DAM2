package com.example.roomproject.data.model

import com.example.roomproject.domain.Comic
import com.example.roomproject.domain.Series
import com.example.roomproject.domain.SuperHero
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun SuperHeroEntity.superHeroEntityToDomain() : SuperHero{
    return SuperHero(id, name, description,imageUrl, modifiedDate, listComics?.map(ComicEntity::comicEntityToDomain), listSeries?.map(SeriesEntity::seriesEntityToDomain));
}

fun SuperHero.superHeroDomainToEntity() : SuperHeroEntity{
    return SuperHeroEntity(id, name, description, imageUrl, modifiedDate, comicsList?.map(Comic::comicToEntity), seriesList?.map(Series::seriesToEntity));
}

fun ComicEntity.comicEntityToDomain() : Comic{
    return Comic(id, name, publishedDate)
}

fun Comic.comicToEntity() : ComicEntity{
    return ComicEntity(id, name, publishedDate)
}

fun SeriesEntity.seriesEntityToDomain() : Series{
    return Series(id, name, publishDate)
}

fun Series.seriesToEntity() : SeriesEntity{
    return SeriesEntity(id, name, publishedDate)
}