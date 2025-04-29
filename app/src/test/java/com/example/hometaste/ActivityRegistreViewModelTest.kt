package com.example.hometaste

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hometaste.viewmodel.ActivityRegistreViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class ActivityRegistreViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel = ActivityRegistreViewModel()


    /*
     * TEST DE USERNAME
     */

    @Test
    fun `validar camp d’usuari buit i que retorni l’error corresponent`() {
        viewModel.updateUserName("")
        assertEquals("ERROR: El nombre de campo de usuario no puede estar vacío.", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `validar camp d’usuari massa curt i que retorni l’error corresponent`() {
        viewModel.updateUserName("eu")
        assertEquals("ERROR: El nombre de usuario debe tener entre 5 y 20 carácteres alfanuméricos.", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `validar camp d’usuari massa llarg i que retorni l’error corresponent`() {
        viewModel.updateUserName("supercalifragilisticoespialidoso")
        assertEquals("ERROR: El nombre de usuario debe tener entre 5 y 20 carácteres alfanuméricos.", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `validar camp d’usuari amb caràcters invàlids i que retorni l’error corresponent`() {
        viewModel.updateUserName("usuariInvalid#123")
        assertEquals("ERROR: El nombre de usuario sólo puede contener carácteres alfanuméricos o “_”, “-” y “.”.", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `validar camp d’usuari que comenci per un carácter que no sigui lletra i que retorni l’error corresponent`() {
        viewModel.updateUserName("!usuariInvalid")
        assertEquals("ERROR: El nombre de usuario debe iniciar con una letra.", viewModel.errorNomUsuari.value)
    }

    @Test
    fun `validar camp d’usuari correcte`() {
        viewModel.updateUserName("usuari_Valid")
        assertEquals("Entrada aceptada", viewModel.errorNomUsuari.value)
    }


    /*
     * TEST DE EMAIL
     */

    @Test
    fun `validar camp email buit i que retorni l’error corresponent`() {
        viewModel.updateUserEmail("")
        assertEquals("ERROR: El correo no puede estar vacío.", viewModel.errorEmail.value)
    }

    @Test
    fun `validar camp email format incorrecte sense arroba`() {
        viewModel.updateUserEmail("usuario.gmail.com")
        assertEquals("ERROR: Introduce un correo electrónico válido, que contiene un “@”.", viewModel.errorEmail.value)
    }

    @Test
    fun `validar camp email format incorrecte sense domini`() {
        viewModel.updateUserEmail("usuario@")
        assertEquals("ERROR: Introduce un correo electrónico válido que tenga un dominio.", viewModel.errorEmail.value)
    }

    @Test
    fun `validar camp email amb format correcte`() {
        viewModel.updateUserEmail("usuari@gmail.com")
        assertEquals("Entrada aceptada", viewModel.errorEmail.value)
    }


    /*
     * TEST DE PASSWORD Y CONFIRM PASSWORD
     */

    @Test
    fun `valida contrasenya i retorna error quan es buida`() {
        viewModel.updateUserPassword("")
        assertEquals("La contraseña no puede estar vacía.", viewModel.errorUserPassword.value)
    }

    @Test
    fun `valida contrasenya i retorna error quan no té majúscules, minúscules, numero, símbol ni 8 caracters`() {
        viewModel.updateUserPassword("asdasd2")
        assertEquals(
            "La contraseña al menos debe tener 8 caracteres y contener una mayúscula, una minúscula, un número y un simbolo - / : ; & @ ! %",
            viewModel.errorUserPassword.value
        )
    }

    @Test
    fun `valida contrasenya i retorna correcte quan té majúscules, minúscules, un numero, un símbol i 8 caracters`() {
        viewModel.updateUserPassword("MiNombre@123")
        assertEquals("La contraseña pasa los filtros", viewModel.errorUserPassword.value)
    }

    @Test
    fun `valida contrasenya de confirmacio i retorna error quan es buida`() {
        viewModel.updateConfirmUserPassword("")
        assertEquals("La contraseña no puede estar vacía.", viewModel.errorConfirmPassword.value)
    }

    @Test
    fun `valida contrasenya i retorna error quan no coincideixen`() {
        viewModel.updateUserPassword("MiNombre@123")
        viewModel.updateConfirmUserPassword("Masd!sda232@")

        assertEquals("Las contraseñas no coinciden", viewModel.errorConfirmPassword.value)
    }

    @Test
    fun `valida confirmacion contrasenya i retorna correcte quan coincideixen`() {
        viewModel.updateUserPassword("M@yúsculas1")
        viewModel.updateConfirmUserPassword("M@yúsculas1")

        assertEquals("Las contraseñas si coinciden", viewModel.errorConfirmPassword.value)
    }

    /*
     * VALIDA SI PASAN TODAS LAS PRUEBAS
     */

    @Test
    fun `validar formulari complet i correcte`() {
        viewModel.updateUserName("usuari_Valid")
        viewModel.updateUserPassword("M@yúsculas1")
        viewModel.updateConfirmUserPassword("M@yúsculas1")
        viewModel.updateUserEmail("usuari@gmail.com")

        assertEquals(true, viewModel.formularivalid.value)
    }
}