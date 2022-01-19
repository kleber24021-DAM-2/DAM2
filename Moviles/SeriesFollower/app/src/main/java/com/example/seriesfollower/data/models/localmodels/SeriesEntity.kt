package com.example.seriesfollower.data.models.localmodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "SERIES")
data class SeriesEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "SERIES_ID")
    var id:Int,
    @ColumnInfo(name = "SERIES_TITLE")
    var title:String,
    @ColumnInfo(name = "SERIES_NEXT_RELEASE")
    var nextEpisodeToAir: LocalDate?,
    @ColumnInfo(name= "SERIES_IMAGE")
    var mainImage:String,
    @ColumnInfo(name = "SERIES_OVERVIEW")
    var overview:String,
    @ColumnInfo(name = "SERIES_VOTEAVERAGE")
    var voteAverage:Double
)