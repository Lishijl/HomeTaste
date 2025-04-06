package com.example.hometaste.recipies

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
// import com.example.hometaste.MyRecipies
import com.example.hometaste.R
import com.example.hometaste.data.DataStoreManager
import com.example.hometaste.data.RecipeAPI
import com.example.hometaste.databinding.ItemRecipeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Conecta elementos con el RecyclerView, con la lista de recetas

// La lista de recetas es mutable porque necesitamos actualizarla dinámicamente.
// Recibimos lifecycleScope desde la Activity para gestionar las corrutinas de forma segura con el ciclo de vida de la Activity.

class RecipeAdapter(
    var llistatReceptes: MutableList<Recipe>,
    private val lifecycleScope: CoroutineScope,
    private val listSizeTextView: TextView,
    private val dataStoreManager: DataStoreManager
) : RecyclerView.Adapter<RecipeAdapterHolder>() {

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
            if (url.isNotEmpty()) {
                Glide.with(holder.itemView.context)
                    .load(url) // Cargar la URL de la imagen
                    .error(R.drawable.imagen_default) // si hay un error al escribir la URL pone la imagen default
                    .into(holder.binding.recipeImage) // Asignar la imagen al ImageView
            } else {
                // si la url está vacía pone la imagen default
                holder.binding.recipeImage.setImageResource(R.drawable.imagen_default)
            }
            // si la url es null pone la imagen default
        } ?: holder.binding.recipeImage.setImageResource(R.drawable.imagen_default)

        // Configurar eliminación con confirmación
        holder.binding.eliminar.setOnClickListener {
            showDeleteConfirmationDialog(holder.itemView.context) {
                deleteRecipeFromAPI(recipe.idRecipe, position)
            }
        }

        // Manejar clics en el ítem e imagen
        holder.itemView.setOnClickListener {
            this.recipeClick?.invoke(holder, recipe, position)
        }

        holder.binding.recipeImage.setOnClickListener {
            this.imageClick?.invoke(holder, recipe, position)
        }

        holder.binding.editar.setOnClickListener {
            showEditDialog(holder.itemView.context, recipe) { updatedRecipe ->
                updateRecipeInAPI(updatedRecipe, position)
            }
        }
    }

    // Mostrar ventanna de editar
    private fun showEditDialog(context: Context, recipe: Recipe, onUpdate: (Recipe) -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_recipe, null)

        val editTextNombre = dialogView.findViewById<EditText>(R.id.editTextNombre)
        val editTextDescripcion = dialogView.findViewById<EditText>(R.id.editTextDescripcion)
        val editTextTiempo = dialogView.findViewById<EditText>(R.id.editTextTiempo)
        val editTextDificultad = dialogView.findViewById<EditText>(R.id.editTextDificultad)
        val editTextRaciones = dialogView.findViewById<EditText>(R.id.editTextRaciones)
        val editTextImagen = dialogView.findViewById<EditText>(R.id.editTextImagen)

        editTextNombre.setText(recipe.nombre)
        editTextDescripcion.setText(recipe.descripcion)
        editTextTiempo.setText(recipe.tiempo.toString())
        editTextDificultad.setText(recipe.dificultad)
        editTextRaciones.setText(recipe.raciones.toString())
        editTextImagen.setText(recipe.imagen)

        AlertDialog.Builder(context)
            .setTitle("Editar Receta")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val updatedRecipe = Recipe(
                    idRecipe = recipe.idRecipe, // No se cambia
                    nombre = editTextNombre.text.toString(),
                    descripcion = editTextDescripcion.text.toString(),
                    tiempo = editTextTiempo.text.toString().toInt(),
                    dificultad = editTextDificultad.text.toString(),
                    raciones = editTextRaciones.text.toString().toInt(),
                    imagen = editTextImagen.text.toString()
                )
                onUpdate(updatedRecipe)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun updateRecipeInAPI(updatedRecipe: Recipe, position: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RecipeAPI.API().updateRecipe(updatedRecipe.idRecipe, updatedRecipe)
                if (response.isSuccessful) {
                    dataStoreManager.incrementEditCount()
                    withContext(Dispatchers.Main) {
                        llistatReceptes[position] = updatedRecipe
                        notifyItemChanged(position)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Mostrar alerta de confirmación
    private fun showDeleteConfirmationDialog(context: Context, onConfirm: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Confirmación")
            .setMessage("¿Está seguro que quiere eliminar esta receta?")
            .setPositiveButton("Confirmar") { _, _ -> onConfirm() }
            .setNegativeButton("Cancelar", null)
            .show()
    }


    // Función para eliminar receta de la API y actualizar RecyclerView
    private fun deleteRecipeFromAPI(idRecipe: Int, position: Int) {
        // Usamos lifecycleScope.launch en lugar de CoroutineScope para asegurar que se cancele si la Activity es destruida
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Llamamos al endpoint de eliminar receta de la API
                val response = RecipeAPI.API().deleteRecipe(idRecipe)
                if (response.isSuccessful) {
                    dataStoreManager.incrementDeleteCount()
                    withContext(Dispatchers.Main) {
                        // Si la eliminación fue exitosa, eliminamos la receta de la lista y notificamos al adapter

                        llistatReceptes.removeAt(position)// Aquí eliminamos el ítem de la lista mutable
                        notifyItemRemoved(position) // Notificamos al RecyclerView que el ítem fue eliminado

                        // ACTUALIZAMOS EL CONTADOR DIRECTAMENTE
                        listSizeTextView.text = "Mis Recetas (${llistatReceptes.size})"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private var recipeClick: ((holder: RecipeAdapterHolder, model: Recipe, position: Int) -> Unit)? =
        null

    fun setOnRecipeClick(recipeClickCallback: (holder: RecipeAdapterHolder, model: Recipe, position: Int) -> Unit) {
        this.recipeClick = recipeClickCallback
    }

    private var imageClick: ((holder: RecipeAdapterHolder, model: Recipe, position: Int) -> Unit)? =
        null

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
    }
}
