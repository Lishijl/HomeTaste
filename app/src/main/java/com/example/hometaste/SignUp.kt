package com.example.hometaste

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
    private val model: ActivityRegistreViewModel by viewModels()
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userName.addTextChangedListener {
            model.updateUserName(it.toString())
        }

        binding.userEmail.addTextChangedListener {
            model.updateUserEmail(it.toString())
        }

        binding.userPswwd.addTextChangedListener {
            model.updateUserPassword(it.toString())
        }

        binding.userPswwdConfirm.addTextChangedListener {
            model.updateConfirmUserPassword(it.toString())
        }

        binding.registerButton.setOnClickListener {
            if (model.formularivalid.value == true) {
                Toast.makeText(this, "Registro completado correctamente", Toast.LENGTH_SHORT).show();
                goToLogin(it);
            }
        }

        model.errorNomUsuari.observe(this) { errorUserName ->
            binding.userName.setError(errorUserName)
        }

        model.errorEmail.observe(this) { errorUserEmail ->
            binding.userEmail.setError(errorUserEmail)
        }

        model.errorUserPassword.observe(this) { errorUserPsswd ->
            binding.userPswwd.error = errorUserPsswd
        }

        model.errorConfirmPassword.observe(this) { errorUserPsswdConfirm ->
            binding.userPswwdConfirm.error = errorUserPsswdConfirm
        }

        model.formularivalid.observe(this) { isValid ->
            binding.registerButton.isEnabled = isValid
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