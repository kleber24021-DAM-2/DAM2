package com.example.roomproject.domain

import java.time.LocalDate

class Comic(
    val id: Int,
    val name: String,
    val publishedDate: LocalDate
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comic

        if (id != other.id) return false
        if (name != other.name) return false
        if (publishedDate != other.publishedDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + publishedDate.hashCode()
        return result
    }

    override fun toString(): String {
        return "Nombre = '$name', Fecha publicación = $publishedDate"
    }


}