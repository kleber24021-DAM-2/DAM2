package com.example.roomproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class SeriesEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "COMIC_ID")
    val id:Int,
    @ColumnInfo(name = "NAME")
    val name:String,
    @ColumnInfo(name="DATE")
    val publishDate:LocalDate
        )