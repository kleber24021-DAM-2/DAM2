package com.example.roomproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "HEROES")
data class SuperHeroEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "HERO_ID")
    val id:Int,
    @ColumnInfo(name = "NAME")
    val name:String,
    @ColumnInfo(name = "DESCRIPTION")
    val description:String,
    @ColumnInfo(name = "IMAGE")
    val imageUrl:String,
    @ColumnInfo(name = "DATE")
    val modifiedDate:LocalDate,
)