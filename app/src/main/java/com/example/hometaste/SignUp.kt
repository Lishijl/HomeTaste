package com.example.hometaste

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.hometaste.databinding.ActivityMyRecipiesBinding
import com.example.hometaste.databinding.ActivitySignUpBinding
import com.example.hometaste.viewmodel.ActivityRegistreViewModel

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val model: ActivityRegistreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userEmail.addTextChangedListener {
            model.updateUserEmail(it.toString())
        }

        model.errorEmail.observe(this) { errorEmailUser ->
            binding.userEmail.setError(errorEmailUser)
        }
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