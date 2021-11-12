package com.example.roomproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "COMICS", foreignKeys = [
    ForeignKey(entity = SuperHeroEntity::class,
    parentColumns = ["HERO_ID"],
    childColumns = ["SUPERHERO_ID"])
])
data class ComicEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "COMIC_ID")
    val id:Int,
    @ColumnInfo(name = "NAME")
    val name:String,
    @ColumnInfo(name = "DATE")
    val publishedDate:LocalDate,
    @ColumnInfo(name = "SUPERHERO_ID")
    val superHeroId:Int
        )