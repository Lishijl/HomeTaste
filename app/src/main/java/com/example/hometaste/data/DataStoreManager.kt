package com.example.hometaste.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "DATA_ESTADISTICA")

// es lo mateix que fer crear un dataStore al context, pero mes explicit
//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DATA_ESTADISTICA")

class DataStoreManager(private val context: Context) {
    // aquí posariem els valors clau-valor
    private val CREATE = intPreferencesKey("crear")
    private val EDIT = intPreferencesKey("editar")
    private val DELETE = intPreferencesKey("borrar")

    suspend fun createRecipe(quantesCreades: Int) {
        context.dataStore.edit { comptadorCrear ->
            comptadorCrear[CREATE] = quantesCreades
        }
    }
}