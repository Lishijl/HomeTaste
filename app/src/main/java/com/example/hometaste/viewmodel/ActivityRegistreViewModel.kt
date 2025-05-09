package com.example.hometaste.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityRegistreViewModel : ViewModel() {

    private var _userName:String=""
    private var _email :String=""
    private var _userPassword :String=""
    private var _userConfirmPassword : String=""

    private val _formularivalid=MutableLiveData<Boolean>(false)
    val formularivalid:MutableLiveData<Boolean> = _formularivalid

    private val _errorNomUsuari=MutableLiveData<String>("")
    val errorNomUsuari:LiveData<String> = _errorNomUsuari

    private val _errorUserPassword=MutableLiveData<String>("")
    val errorUserPassword:LiveData<String> = _errorUserPassword

    private val _errorConfirmPassword = MutableLiveData("")
    val errorConfirmPassword: LiveData<String> = _errorConfirmPassword

    private val _errorEmail = MutableLiveData<String>("")
    val errorEmail: LiveData<String> = _errorEmail



    // MÉTODOS USERNAME
    fun updateUserName(nomusuari: String) {
        _userName = nomusuari
        checkUserName()
    }

    private fun checkUserName() {
        _errorNomUsuari.value = "El nombre de usuario és obligatorio"
        if (_userName.isEmpty()) {
            return
        }

        if (_userName.length < 5 || _userName.length > 20) {
            _errorNomUsuari.value =
                "El nombre de usuario debe tener entre 5 y 20 carácteres alfanuméricos."
            return
        }

        val usernameRegex =
            Regex("^[a-zA-Z][a-zA-Z0-9_.-]{4,19}$") // empieza con letra, permite letras, números, _.- y de 5 a 20
        if (!usernameRegex.matches(_userName)) {
            if (!_userName[0].isLetter()) {
                _errorNomUsuari.value = "El nombre de usuario debe iniciar con una letra."
            } else {
                _errorNomUsuari.value =
                    "El nombre de usuario sólo puede contener carácteres alfanuméricos o “_”, “-” y “.”."
            }
            return
        }

        _errorNomUsuari.value = "Entrada aceptada"
        validaForm()
    }



    // MÉTODOS EMAIL
    fun updateUserEmail(email: String) {
        _email = email
        checkUserEmail()
    }

    private fun checkUserEmail() {
        if (_email.isEmpty()) {
            _errorEmail.value = "El correo electrónico és obligatorio"
            return
        }

        if ('@' !in _email) {
            _errorEmail.value = "Introduce un correo electrónico válido, que contiene un “@”."
            return
        }

        val parts = _email.split("@")
        if (parts.size != 2 || parts[1].isEmpty() || !parts[1].contains(".")) {
            _errorEmail.value = "Introduce un correo electrónico válido que tenga un dominio."
            return
        }

        _errorEmail.value = "Entrada aceptada"
        validaForm()
    }



    // MÉTODOS PASSWORD
    fun updateUserPassword(password: String) {
        _userPassword = password
        checkUserPassword()
        checkPasswordsMatch()
    }


    private fun checkUserPassword() {
        if (_userPassword.isEmpty()) {
            _errorUserPassword.value = "La contraseña és obligatoria"
            return
        }

        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-/:;&@!%]).{8,}\$")
        if (!regex.matches(_userPassword)) {
            _errorUserPassword.value = "La contraseña al menos debe tener 8 caracteres y contener una mayúscula, una minúscula, un número y un simbolo - / : ; & @ ! %"
            return
        }
        // "" string buit, en test integracio
        _errorUserPassword.value = ""
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
        } else if (_userPassword != _userConfirmPassword) {
            _errorConfirmPassword.value = "Las contraseñas no coinciden"
        } else {
            _errorConfirmPassword.value = "Las contraseñas si coinciden"
        }

        validaForm()
    }

    fun validaForm() {
        val isValid = errorNomUsuari.value == "Entrada aceptada" &&
                errorUserPassword.value == "La contraseña pasa los filtros" &&
                errorConfirmPassword.value == "Las contraseñas si coinciden" &&
                errorEmail.value == "Entrada aceptada"

        _formularivalid.value = isValid
    }
}