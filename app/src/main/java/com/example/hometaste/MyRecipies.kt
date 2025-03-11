package com.example.hometaste

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.hometaste.data.RecipeAPI
import com.example.hometaste.databinding.ActivityMyRecipiesBinding
import com.example.hometaste.recipies.RecipeAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyRecipies : AppCompatActivity() {
    private lateinit var binding: ActivityMyRecipiesBinding

    // declarem dos variables per controlar el RV
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter

    private lateinit var listSize: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        binding = ActivityMyRecipiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.selectedItemId = R.id.recetas
        binding.bottomNavigationView.setOnItemSelectedListener(bottomNavListener)

        // Inicializamos RecyclerView y Adapter
        recyclerView = binding.recipesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)


        // Llamamos a la API para obtener las recetas
        fetchRecipes()
    }

    private fun fetchRecipes() {
        lifecycleScope.launch {
            try {
                // Obtenemos la lista de recetas desde la API
                val recipeList = RecipeAPI.API().recipeList().toMutableList()

                if (recipeList.isNotEmpty()) {
                    // Usamos lifecycleScope para evitar problemas de memoria y que se ejecute en el hilo principal
                    withContext(Dispatchers.Main) {
                        // Pasamos lifecycleScope al adaptador para manejar las corrutinas dentro de él
                        adapter = RecipeAdapter(recipeList, lifecycleScope)
                        recyclerView.adapter = adapter
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private val bottomNavListener = fun(item: MenuItem): Boolean{
        when (item.itemId) {
            R.id.home -> {
                // Cambia a MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
            R.id.buscar -> {
                // Cambia a Filter
                startActivity(Intent(this, Filter::class.java))
                return true
            }
        }
        return false
    }
    // considerar si usar con API
    fun searchRecipe(view: View) {
    }
}