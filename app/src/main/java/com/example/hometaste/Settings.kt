package com.example.hometaste

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hometaste.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener(bottomNavListener)
    }


    private val bottomNavListener = fun(item: MenuItem): Boolean {
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

            R.id.recetas -> {
                // Cambia a MyRecipies
                startActivity(Intent(this, MyRecipies::class.java))
                return true
            }
            R.id.estadisticas -> {
                // Cambia a estadistica
                startActivity(Intent(this, Graphics::class.java))
                return true
            }
        }
        return false
    }
}