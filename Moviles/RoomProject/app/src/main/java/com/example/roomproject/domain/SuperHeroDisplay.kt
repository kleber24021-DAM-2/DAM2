package com.example.roomproject.domain

//Esta clase se utiliza para mostrar los héroes en la lista, es diferente
//al héroe del detalle, porque tiene menos datos

data class SuperHeroDisplay(
    val id: Int,
    val name: String,
    val imageUrl: String
)