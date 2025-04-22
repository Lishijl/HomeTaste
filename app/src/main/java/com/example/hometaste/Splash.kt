package com.example.hometaste

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener la referencia del ImageView
        val logoImageView = findViewById<View>(R.id.main).findViewById<View>(R.id.logo_splash) // Ajusta si tu ID es diferente

        // Cargar la animación desde resources
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_scale) // Definiremos este archivo abajo
        logoImageView.startAnimation(animation) // Inicia la animación en el ImageView

        // Ejecutar el código con un retraso en el hilo principal sin bloquear la interfaz.
        Handler(Looper.getMainLooper()).postDelayed({
            // Cambia de actividad a la actividad principial provisional después de 1s
            val intent = Intent(this, Graphics::class.java)
            startActivity(intent)
            finish() }, 2000) // 2000 ms = 2 segundo
    }

}