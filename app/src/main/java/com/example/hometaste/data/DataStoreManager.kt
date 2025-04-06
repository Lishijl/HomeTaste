package com.example.hometaste.data

import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "DATA_ESTADISTICA")

// es lo mateix que fer crear un dataStore al context, pero mes explicit
//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DATA_ESTADISTICA")

class DataStoreManager(private val context: Context) {
    // aquí posariem els valors clau-valor
    private val CREATE = intPreferencesKey("crear")
    private val EDIT = intPreferencesKey("editar")
    private val DELETE = intPreferencesKey("borrar")
    private val SEARCH = intPreferencesKey("buscar")

    suspend fun createRecipe(quantesCreades: Int) {
        context.dataStore.edit { comptadorCrear ->
            comptadorCrear[CREATE] = quantesCreades
        }
    }
    suspend fun editRecipe(quantesEditades: Int) {
        context.dataStore.edit { comptadorEditar ->
            comptadorEditar[EDIT] = quantesEditades
        }
    }
    suspend fun deleteRecipe(quantesBorrades: Int) {
        context.dataStore.edit { comptadorBorrar ->
            comptadorBorrar[DELETE] = quantesBorrades
        }
    }
    suspend fun searchRecipe(quantesBuscades: Int) {
        context.dataStore.edit { comptadorBuscar ->
            comptadorBuscar[SEARCH] = quantesBuscades
        }
    }
    suspend fun incrementCreateCount() {
        val current = getCreateCount().first()
        createRecipe(current + 1)
    }
    suspend fun incrementEditCount() {
        val current = getEditCount().first()
        editRecipe(current + 1)
    }
    suspend fun incrementDeleteCount() {
        val current = getDeleteCount().first()
        deleteRecipe(current + 1)
    }
    suspend fun incrementSearchCount() {
        val current = getSearchCount().first()
        searchRecipe(current + 1)
    }
    fun getCreateCount(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[CREATE] ?: 0
        }
    }
    fun getEditCount(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[EDIT] ?: 0
        }
    }
    fun getDeleteCount(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[DELETE] ?: 0
        }
    }
    fun getSearchCount(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[SEARCH] ?: 0
        }
    }
}