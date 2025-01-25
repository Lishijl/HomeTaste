package com.example.hometaste.recipies

import com.example.hometaste.R

// model de data
data class Recipe (
    val idRecipe: Int,
    val name: String,
    val description: String,
    val time: Int,
    val skillLvl: String,
    val serving: Int,
    val image: Int?
)

// creem un proveidor per oferir una llista dels objectes Recipe Data
// accessible amb RecipiesProvider.RecipesList, son com data de simulació,
// quan encara no tenim api
class RecipiesProvider{
    companion object {
        val RecipesList:List<Recipe> = listOf(
            Recipe(1, "Croquetas de jamón", "Bechamel, jamoncitos...", 25, "Media", 4,
                R.drawable.croquetas
            ),
            Recipe(2, "Margherita", "Harina, tomate, mozzarella...", 45, "Fácil", 2,
                R.drawable.margherita
            )
        )
    }
}