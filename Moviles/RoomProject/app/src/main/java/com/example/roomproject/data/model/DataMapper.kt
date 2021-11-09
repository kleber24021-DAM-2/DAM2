package com.example.roomproject.data.model

import com.example.roomproject.domain.SuperHero
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun SuperHeroEntity.superHeroEntityToDomain() : SuperHero{
    val date = LocalDate.parse(modifiedDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    return SuperHero(id, name, description,imageUrl, date);
}

fun SuperHero.superHeroDomainToEntity() : SuperHeroEntity{
    return SuperHeroEntity(id, name, description, imageUrl, modifiedDate.toString());
}