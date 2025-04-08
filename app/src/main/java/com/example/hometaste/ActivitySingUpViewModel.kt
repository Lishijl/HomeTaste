package com.example.hometaste

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class EstatRegistre(
    var esValid: Boolean,
    var errorNomUsuari: String?,
    var errorCorreu: String?,
    var errorContrasenya: String?,
)

class ActivitySingUpViewModel : ViewModel() {
    private var _nomUsuari: String = ""
    private var _correu: String = ""
    private var _contrasenya: String = ""

    private val _validacioDades = MutableLiveData<EstatRegistre>()
    val validacioDades = LiveData<EstatRegistre>() = _validacioDades

    fun actualitzanomUsuari(nomusuari: String) {
        _nomUsuari = nomusuari
        // canvia estat de variable
        comprova_nomusuari()
    }
    // el test crida aquesta funcio
    private fun comprova_nomusuari() {
        if(_nomUsuari.isEmpty()) {
            _validacioDades.value?.errorNomUsuari = "El nom d'usuari no pot ser buit."
        }
    }

    fun actualitzaemail(email: String) {

    }
    fun comprovadadesusuari() {}

    // falta crear en els tests de viewModel instance, per tirar proves contra les funcionalitats i metodes d'aquí
}