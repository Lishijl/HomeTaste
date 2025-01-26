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
import com.example.hometaste.databinding.ActivityMyRecipiesBinding
import com.example.hometaste.recipies.RecipeAdapter
import com.example.hometaste.recipies.RecipiesProvider
import com.google.android.material.bottomnavigation.BottomNavigationView

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


        // obtenim la data de simulació desde el provider
        val recipesList = RecipiesProvider.RecipesList
        recyclerView = findViewById(R.id.recipesRecyclerView)
        adapter = RecipeAdapter(recipesList)

        listSize = findViewById(R.id.listSize)
        listSize.setText(buildString {
            append("Mis Recetas (")
            append(adapter.itemCount)
            append(")")
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
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
}