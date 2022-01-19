package com.example.seriesfollower.data.models.localmodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "MOVIES")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "MOVIE_ID")
    var id:Int,
    @ColumnInfo(name = "MOVIE_TITLE")
    var title:String,
    @ColumnInfo(name = "MOVIE_OVERVIEW")
    var overview:String,
    @ColumnInfo(name = "MOVIE_POSTER")
    var poster:String,
    @ColumnInfo(name = "MOVIE_RELEASE_DATE")
    var releaseDate:LocalDate,
    @ColumnInfo(name = "MOVIE_VOTEAVERAGE")
    var voteAverage:Double
    )