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
}