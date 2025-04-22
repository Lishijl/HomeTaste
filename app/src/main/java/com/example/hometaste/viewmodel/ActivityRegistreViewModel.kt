package com.example.hometaste.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ActivityRegistreViewModel {

    private var _userName:String=""
    private var _email :String=""
    private var _userPassword :String=""

    private val _formularivalid=MutableLiveData<Boolean>(false)
    val  formularivalid:MutableLiveData<Boolean> = _formularivalid

    private val _errorNomUsuari=MutableLiveData<String>("")
    val errorNomUsuari:LiveData<String> = _errorNomUsuari

    private val _errorUserPassword=MutableLiveData<String>("")
    val errorUserPassword:LiveData<String> = _errorUserPassword



    // MÉTODOS USERNAME
    fun updateUserName(nomusuari: String) {
        _userName = nomusuari
        checkUserName()
    }

    private fun checkUserName() {
        if (_userName.isEmpty()) {
            _errorNomUsuari.value = "El nombre de usuario es obligatoria"
        }
    }



    // MÉTODOS PASSWORD
    fun updateUserPassword(password: String) {
        _userPassword = password
        checkUserPassword()
    }

    private fun checkUserPassword() {
        if (_userPassword.isEmpty()) {
            _errorUserPassword.value = "La contraseña es obligatoria "
        }
    }
}