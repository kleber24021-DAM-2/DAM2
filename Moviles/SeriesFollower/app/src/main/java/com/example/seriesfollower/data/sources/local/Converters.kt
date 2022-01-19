package com.example.seriesfollower.data.sources.local

import androidx.room.TypeConverter
import com.example.seriesfollower.data.DataConsts
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, DataConsts.DATE_FORMATTER) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.format(DataConsts.DATE_FORMATTER)
    }
}