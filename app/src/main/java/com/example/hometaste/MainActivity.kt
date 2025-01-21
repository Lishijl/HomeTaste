package com.example.hometaste

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        screenSplash.setKeepOnScreenCondition{ false }
    }

    fun goToLogin(view: View) {
        val iLogin = Intent(this, Login::class.java)
        startActivity(iLogin)
    }
    fun goToSignUp(view: View) {
        val iSignUp = Intent(this, SignUp::class.java)
        startActivity(iSignUp)
    }
    fun goToFilter(view: View) {
        val iFilter = Intent(this, Filter::class.java)
        startActivity(iFilter)
    }
    fun goToMyRecipies(view: View) {
        val iMyRecipies= Intent(this, MyRecipies::class.java)
        startActivity(iMyRecipies)
    }
    fun goToSettings(view: View) {
        val iSettings = Intent(this, Settings::class.java)
        startActivity(iSettings)
    }
    fun goToHelp(view: View) {
        val iHelp = Intent(this, Help::class.java)
        startActivity(iHelp)
    }
}