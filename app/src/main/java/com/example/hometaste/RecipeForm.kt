package com.example.hometaste

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.hometaste.data.RecipeAPI
import com.example.hometaste.databinding.ActivityMyRecipiesBinding
import com.example.hometaste.databinding.ActivityRecipeFormBinding
import com.example.hometaste.recipies.Recipe
import com.example.hometaste.recipies.RecipeAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeForm : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecipeFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createRecipe.setOnClickListener {
            val newRecipe = Recipe(
                idRecipe = 0,
                nombre = binding.title.text.toString(),
                descripcion = binding.description.text.toString(),
                tiempo = binding.time.text.toString().toIntOrNull() ?: 1,
                dificultad = binding.skillLvl.text.toString(),
                raciones = binding.serving.text.toString().toIntOrNull() ?: 1,
                imagen = binding.imageUrl.text.toString()
            )
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = RecipeAPI.API().insertRecipe(newRecipe)
                    if (response.isSuccessful) {
                        val intentResult = Intent()
                        intentResult.putExtra("newRecipe", newRecipe)
                        setResult(Activity.RESULT_OK, intentResult)
                        Log.d("RecipeForm", "Resultado enviado correctamente con newRecipe = $newRecipe")
                        finish()
                    } else {
                        Log.e("RecipeAPI", "Error al guardar receta: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}