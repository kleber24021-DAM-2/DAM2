package com.example.crudfromjson.domain.ownmodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
class Hero (val name:String,
            val description:String,
            val dateModified:LocalDateTime,
            val image:String,
            val comicsName:MutableList<String>,
            val seriesName:MutableList<String>) : Parcelable{

    override fun toString(): String {
        return name
    }
}