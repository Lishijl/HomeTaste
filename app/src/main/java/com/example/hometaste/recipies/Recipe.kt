package com.example.hometaste.recipies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// model de data
@Parcelize
data class Recipe (
    val idRecipe: Int,
    val nombre: String,
    val descripcion: String,
    val tiempo: Int,
    val dificultad: String,
    val raciones: Int,
    val imagen: String?
) : Parcelable