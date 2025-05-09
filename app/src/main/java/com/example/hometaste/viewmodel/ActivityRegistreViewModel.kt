package com.example.hometaste.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityRegistreViewModel : ViewModel() {

    private var _userName: String = ""
    private var _email: String = ""
    private var _userPassword: String = ""
    private var _userConfirmPassword: String = ""

    // validación de campos para formValid()
    private var isUserNameValid = false
    private var isUserEmailValid = false
    private var isUserPsswdValid = false
    private var isUserConfirmPsswdValid = false


    private val _formularivalid = MutableLiveData<Boolean>(false)
    val formularivalid: MutableLiveData<Boolean> = _formularivalid

    private val _errorNomUsuari = MutableLiveData<String>("")
    val errorNomUsuari: LiveData<String> = _errorNomUsuari

    private val _errorEmail = MutableLiveData<String>("")
    val errorEmail: LiveData<String> = _errorEmail

    private val _errorUserPassword = MutableLiveData<String>("")
    val errorUserPassword: LiveData<String> = _errorUserPassword

    private val _errorConfirmPassword = MutableLiveData<String>("")
    val errorConfirmPassword: LiveData<String> = _errorConfirmPassword


    // MÉTODOS USERNAME
    fun updateUserName(nomusuari: String) {
        _userName = nomusuari
        checkUserName()
    }

    private fun checkUserName() {
        if (_userName.isEmpty()) {
            _errorNomUsuari.value = "El nombre de usuario és obligatorio"
            isUserNameValid = false
        } else if (_userName.length < 5 || _userName.length > 20) {
            _errorNomUsuari.value =
                "El nombre de usuario debe tener entre 5 y 20 carácteres alfanuméricos."
            isUserNameValid = false
        } else if (!Regex("^[a-zA-Z][a-zA-Z0-9_.-]{4,19}$").matches(_userName)) {
            if (!_userName[0].isLetter()) {
                _errorNomUsuari.value = "El nombre de usuario debe iniciar con una letra."
            } else {
                _errorNomUsuari.value =
                    "El nombre de usuario sólo puede contener carácteres alfanuméricos o “_”, “-” y “.”."
            }
            isUserNameValid = false
        } else {
            // "" porque no hay error
            _errorNomUsuari.value = ""
            isUserNameValid = true
        }
        validaForm()
    }


    // MÉTODOS EMAIL
    fun updateUserEmail(email: String) {
        _email = email
        checkUserEmail()
    }

    private fun checkUserEmail() {
        val parts = _email.split("@")

        if (_email.isEmpty()) {
            _errorEmail.value = "El correo electrónico és obligatorio"
            isUserEmailValid = false
        } else if ('@' !in _email) {
            _errorEmail.value = "Introduce un correo electrónico válido, que contiene un “@”."
            isUserEmailValid = false
        } else if (parts.size != 2 || parts[1].isEmpty() || !parts[1].contains(".")) {
            _errorEmail.value = "Introduce un correo electrónico válido que tenga un dominio."
            isUserEmailValid = false
        } else {
            // "" porque no hay error
            _errorEmail.value = ""
            isUserEmailValid = true
        }
        validaForm()
    }


    // MÉTODOS PASSWORD
    fun updateUserPassword(password: String) {
        _userPassword = password
        checkUserPassword()
        checkPasswordsMatch()
    }


    private fun checkUserPassword() {
        val regexPsswd = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-/:;&@!%]).{8,}\$")

        if (_userPassword.isEmpty()) {
            _errorUserPassword.value = "La contraseña és obligatoria"
            isUserPsswdValid = false
        } else if (!regexPsswd.matches(_userPassword)) {
            _errorUserPassword.value =
                "La contraseña al menos debe tener 8 caracteres y contener una mayúscula, una minúscula, un número y un simbolo - / : ; & @ ! %"
            isUserPsswdValid = false
        } else {
            // "" porque no hay error
            _errorUserPassword.value = ""
            isUserPsswdValid = true
        }
        validaForm()
    }


    // MÉTODOS CONFIRM PASSWORD
    fun updateConfirmUserPassword(password: String) {
        _userConfirmPassword = password
        checkPasswordsMatch()
    }

    private fun checkPasswordsMatch() {
        if (_userConfirmPassword.isEmpty()) {
            _errorConfirmPassword.value = "La contraseña de confirmación és obligatoria"
            isUserConfirmPsswdValid = false
        } else if (_userPassword != _userConfirmPassword) {
            _errorConfirmPassword.value = "Las contraseñas no coinciden"
            isUserConfirmPsswdValid = false
        } else {
            // "" porque no hay error
            _errorConfirmPassword.value = ""
            isUserConfirmPsswdValid = true
        }
        validaForm()
    }

    fun validaForm() {
        _formularivalid.value =
            isUserNameValid && isUserEmailValid &&
            isUserPsswdValid && isUserConfirmPsswdValid
    }
}