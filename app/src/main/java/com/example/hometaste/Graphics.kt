package com.example.hometaste

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.hometaste.data.DataExperience
import com.example.hometaste.data.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class Graphics : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_graphics)
        lifecycleScope.launch(Dispatchers.IO) {
            getDataExperience().collect {
                // assignar valors a les columnes de data

            }
        }
    }
    private fun getDataExperience() = dataStore.data.map { data ->
        DataExperience(
            create = data[intPreferencesKey("crear")] ?: 0,
            edit = data[intPreferencesKey("editar")] ?: 0,
            delete = data[intPreferencesKey("borrar")] ?: 0
        )
    }
}