package com.example.roomproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "SERIES",foreignKeys = [
    ForeignKey(entity = SuperHeroEntity::class,
        parentColumns = ["HERO_ID"],
        childColumns = ["SUPERHERO_ID"])
])
data class SeriesEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SERIES_ID")
    val id:Int,
    @ColumnInfo(name = "NAME")
    val name:String,
    @ColumnInfo(name="DATE")
    val publishDate:LocalDate,
    @ColumnInfo(name="SUPERHERO_ID")
    val superHeroId: Int,
        )