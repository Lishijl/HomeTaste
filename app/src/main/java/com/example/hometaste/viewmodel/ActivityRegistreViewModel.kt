package com.example.hometaste.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ActivityRegistreViewModel {

    private var _userName:String=""
    private var _email :String=""
    private var _userPassword :String=""
    private var _userConfirmPassword : String=""

    private val _formularivalid=MutableLiveData<Boolean>(false)
    val  formularivalid:MutableLiveData<Boolean> = _formularivalid

    private val _errorNomUsuari=MutableLiveData<String>("")
    val errorNomUsuari:LiveData<String> = _errorNomUsuari

    private val _errorUserPassword=MutableLiveData<String>("")
    val errorUserPassword:LiveData<String> = _errorUserPassword

    private val _errorConfirmPassword = MutableLiveData("")
    val errorConfirmPassword: LiveData<String> = _errorConfirmPassword


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
        checkPasswordsMatch()
    }

    fun updateConfirmUserPassword(password: String) {
        _userConfirmPassword = password
        checkPasswordsMatch()
    }

    private fun checkUserPassword() {
        if (_userPassword.isEmpty()) {
            _errorUserPassword.value = "La contraseña no puede estar vacía."
            return
        }

        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-/:;&@!%]).{8,}\$")
        if (!regex.matches(_userPassword)) {
            _errorUserPassword.value = "La contraseña al menos debe tener 8 caracteres y contener una mayúscula, una minúscula, un número y un simbolo - / : ; & @ ! %"
            return
        }

        _errorUserPassword.value = "La contraseña pasa los filtros"
    }

    private fun checkPasswordsMatch() {
        if (_userConfirmPassword.isEmpty()) {
            _errorConfirmPassword.value = "La contraseña no puede estar vacía."
        } else if (_userPassword != _userConfirmPassword) {
            _errorConfirmPassword.value = "Las contraseñas no coinciden"
        } else {
            _errorConfirmPassword.value = "Las contraseñas si coinciden"
        }
    }
}