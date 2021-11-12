package com.example.roomproject.domain

//Esta clase se utiliza para mostrar los héroes en la lista, es diferente
//al héroe del detalle, porque tiene menos datos

class SuperHeroDisplay (
    val id: Int,
    val name: String,
    val imageUrl:String
        ){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SuperHeroDisplay

        if (id != other.id) return false
        if (name != other.name) return false
        if (imageUrl != other.imageUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + imageUrl.hashCode()
        return result
    }
}
