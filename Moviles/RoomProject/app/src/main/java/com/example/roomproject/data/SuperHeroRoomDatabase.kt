package com.example.roomproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomproject.data.model.ComicEntity
import com.example.roomproject.data.model.SeriesEntity
import com.example.roomproject.data.model.SuperHeroEntity

@Database(entities = [SuperHeroEntity::class, ComicEntity::class, SeriesEntity::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class SuperHeroRoomDatabase : RoomDatabase() {
    abstract fun superHeroDao():SuperHeroDao

    companion object{
        @Volatile
        private var INSTANCE: SuperHeroRoomDatabase? = null

        fun getDatabase(context : Context): SuperHeroRoomDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SuperHeroRoomDatabase::class.java,
                    "item_database"
                )
                    .createFromAsset("database/heroes.db")
                    .fallbackToDestructiveMigrationFrom(4)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}