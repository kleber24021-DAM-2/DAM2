package com.example.roomproject.data

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        val formatter = DateTimeFormatter.ofPattern(Constants.formatter)
        return value?.let { LocalDate.parse(it, formatter) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        val formatter = DateTimeFormatter.ofPattern(Constants.formatter)
        return date?.format(formatter)
    }
}