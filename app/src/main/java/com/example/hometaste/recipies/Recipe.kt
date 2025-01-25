package com.example.hometaste.recipies

data class Recipe (
    var idRecipe: Int,
    var name: String,
    var description: String,
    var time: Int,
    var skillLvl: String,
    var serving: String,
    var image: String?
)