package com.example.crudfromjson.domain.ownmodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
class SuperHero(
    val id: Int,
    val name: String,
    val description: String,
    val dateModified: LocalDateTime,
    val imageUrl: String,
    val comicsName: MutableList<String>,
    val seriesName: MutableList<String>
) : Parcelable {

    override fun toString(): String {
        return name
    }
}