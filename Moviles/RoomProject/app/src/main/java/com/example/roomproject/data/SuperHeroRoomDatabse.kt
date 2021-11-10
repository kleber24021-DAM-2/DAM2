package com.example.roomproject.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomproject.data.model.ComicEntity
import com.example.roomproject.data.model.SeriesEntity
import com.example.roomproject.data.model.SuperHeroEntity

@Database(entities = [SuperHeroEntity::class, ComicEntity::class, SeriesEntity::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class SuperHeroRoomDatabse : RoomDatabase() {
    abstract fun superHeroDao():SuperHeroDao
}