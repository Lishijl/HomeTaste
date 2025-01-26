package com.example.hometaste.recipies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hometaste.R

// connecta elements amb RV, amb llista de receptes
class RecipeAdapter(var llistatReceptes:List<Recipe>):RecyclerView.Adapter<RecipeAdapterHolder>() {
    // crea vista per cada element
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapterHolder {
        var itemInflater = LayoutInflater.from(parent.context)
        var recyclerItem = itemInflater.inflate(R.layout.item_recipe, parent, false)
        var holder = RecipeAdapterHolder(recyclerItem)

        return holder
    }
    // retorna el nombre d'elements
    override fun getItemCount(): Int {
        return llistatReceptes.size
    }
    // assigna dades per element
    override fun onBindViewHolder(holder: RecipeAdapterHolder, position: Int) {
        val recipe = llistatReceptes.get(position)
        holder.Renderitzar(recipe)
        holder.itemView.setOnClickListener {
            this.recipeClick?.invoke(holder, recipe, position)
        }
        holder.image.setOnClickListener {
            this.imageClick?.invoke(holder, recipe, position)
        }
    }

    private var recipeClick: ((holder:RecipeAdapterHolder, model: Recipe, position: Int) -> Unit)? = null
    public fun setOnRecipeClick( recipeClickCallback: (holder: RecipeAdapterHolder, model: Recipe, position: Int) -> Unit) {
        this.recipeClick = recipeClickCallback
    }

    private var imageClick: ((holder:RecipeAdapterHolder, model: Recipe, position: Int) -> Unit)? = null
    public fun setOnImageClick( imageClickCallback: (holder: RecipeAdapterHolder, model: Recipe, position: Int) -> Unit) {
        this.imageClick = imageClickCallback
    }

}

// carrega elements a partir de vista
class RecipeAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameRec: TextView = itemView.findViewById(R.id.recipeTitle)
    var description: TextView = itemView.findViewById(R.id.recipeDescription)
    var time: TextView = itemView.findViewById(R.id.recipeTime)
    var skill: TextView = itemView.findViewById(R.id.recipeSkillLvl)
    var serving: TextView = itemView.findViewById(R.id.recipeServing)
    var image: ImageView = itemView.findViewById(R.id.recipeImage)

    public fun Renderitzar(recipe: Recipe) {
        nameRec.text = recipe.name
        description.text = recipe.description
        // aquí m'agradaria obtenir primer el recurs del text existent per omplir
        time.text = buildString {
            append("Tiempo: ")
            append(recipe.time.toString())
            append(" min")
        }
        skill.text = buildString {
            append("Dificultad: ")
            append(recipe.skillLvl)
        }
        serving.text = buildString {
            append("Raciones: ")
            append(recipe.serving)
        }
        recipe.image?.let { image.setImageResource(it) }
    }
}