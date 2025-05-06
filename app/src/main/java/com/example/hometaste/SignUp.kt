package com.example.hometaste

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hometaste.databinding.ActivityMyRecipiesBinding
import com.example.hometaste.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun goToLogin(view: View) {
        val iLogin = Intent(this, Login::class.java)
        startActivity(iLogin)
    }

    fun goToFilter(view: View) {
        val iFilter = Intent(this, Filter::class.java)
        startActivity(iFilter)
    }
}