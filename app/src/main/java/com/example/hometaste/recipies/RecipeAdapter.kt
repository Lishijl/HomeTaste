package com.example.hometaste.recipies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hometaste.R
import com.example.hometaste.data.RecipeAPI
import com.example.hometaste.databinding.ItemRecipeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Conecta elementos con el RecyclerView, con la lista de recetas

// La lista de recetas es mutable porque necesitamos actualizarla dinámicamente.
// Recibimos lifecycleScope desde la Activity para gestionar las corrutinas de forma segura con el ciclo de vida de la Activity.

class RecipeAdapter(var llistatReceptes: MutableList<Recipe>, private val lifecycleScope: CoroutineScope) : RecyclerView.Adapter<RecipeAdapterHolder>() {

    // Crea vista para cada elemento
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapterHolder {
        val itemInflater = LayoutInflater.from(parent.context)
        val recyclerItem = itemInflater.inflate(R.layout.item_recipe, parent, false)
        return RecipeAdapterHolder(recyclerItem)
    }

    // Retorna el número de elementos
    override fun getItemCount(): Int {
        return llistatReceptes.size
    }

    // Asigna datos a cada elemento
    override fun onBindViewHolder(holder: RecipeAdapterHolder, position: Int) {
        val recipe = llistatReceptes[position]
        holder.Renderitzar(recipe)

        // Aquí usamos Glide para cargar la imagen desde la URL
        recipe.imagen?.let { url ->
            Glide.with(holder.itemView.context)
                .load(url) // Cargar la URL de la imagen
                .into(holder.binding.recipeImage) // Asignar la imagen al ImageView
        }

        holder.binding.eliminar.setOnClickListener{
            deleteRecipeFromAPI(recipe.idRecipe, position)
        }

        // Manejar clics en el ítem y en la imagen
        holder.itemView.setOnClickListener {
            this.recipeClick?.invoke(holder, recipe, position)
        }

        holder.binding.recipeImage.setOnClickListener {
            this.imageClick?.invoke(holder, recipe, position)
        }
    }

    // Función para eliminar receta de la API y actualizar RecyclerView
    private fun deleteRecipeFromAPI(idRecipe: Int, position: Int) {
        // Usamos lifecycleScope.launch en lugar de CoroutineScope para asegurar que se cancele si la Activity es destruida
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Llamamos al endpoint de eliminar receta de la API
                val response = RecipeAPI.API().deleteRecipe(idRecipe)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        // Si la eliminación fue exitosa, eliminamos la receta de la lista y notificamos al adapter

                        llistatReceptes.removeAt(position)// Aquí eliminamos el ítem de la lista mutable
                        notifyItemRemoved(position) // Notificamos al RecyclerView que el ítem fue eliminado
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private var recipeClick: ((holder: RecipeAdapterHolder, model: Recipe, position: Int) -> Unit)? = null
    fun setOnRecipeClick(recipeClickCallback: (holder: RecipeAdapterHolder, model: Recipe, position: Int) -> Unit) {
        this.recipeClick = recipeClickCallback
    }

    private var imageClick: ((holder: RecipeAdapterHolder, model: Recipe, position: Int) -> Unit)? = null
    fun setOnImageClick(imageClickCallback: (holder: RecipeAdapterHolder, model: Recipe, position: Int) -> Unit) {
        this.imageClick = imageClickCallback
    }
}

// Carga elementos a partir de la vista
class RecipeAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val binding = ItemRecipeBinding.bind(itemView)

    fun Renderitzar(recipe: Recipe) {
        binding.recipeTitle.text = recipe.nombre // Nombre de la receta
        binding.recipeDescription.text = recipe.descripcion // Descripción de la receta

        binding.recipeTime.text = buildString { // Tiempo de receta
            append("Tiempo: ")
            append(recipe.tiempo.toString())
            append(" min")
        }
        binding.recipeSkillLvl.text = buildString { // Dificultad de receta
            append("Dificultad: ")
            append(recipe.dificultad)
        }
        binding.recipeServing.text = buildString { // Raciones de receta
            append("Raciones: ")
            append(recipe.raciones)
        }

        // Ya no es necesario usar setImageResource porque usamos Glide
        // Glide maneja la carga de la imagen
    }
}
