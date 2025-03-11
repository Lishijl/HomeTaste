package com.example.hometaste.recipies

// model de data

data class Recipe (
    val idRecipe: Int,
    val nombre: String,
    val descripcion: String,
    val tiempo: Int,
    val dificultad: String,
    val raciones: Int,
    val imagen: String?
)