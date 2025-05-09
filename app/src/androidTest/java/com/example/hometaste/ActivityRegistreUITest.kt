package com.example.hometaste

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.withContext

import org.junit.Test
import org.junit.Rule
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ActivityRegistreUITest {
    @get:Rule
    var activityRule = ActivityScenarioRule(SignUp::class.java)
    // caso éxito de todos los campos
    @Test
    fun testRegistroCorrecto() {
        Intents.init()

        onView(withId(R.id.userName)).perform(typeText("usuari_Valid"), closeSoftKeyboard())
        onView(withId(R.id.userEmail)).perform(typeText("usuariValid@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.userPswwd)).perform(typeText("M@yúsculas1"), closeSoftKeyboard())
        onView(withId(R.id.userPswwdConfirm)).perform(typeText("M@yúsculas1"), closeSoftKeyboard())

        // disponibilitat del botó
        onView(withId(R.id.registerButton)).check(matches(isEnabled()))
        onView(withId(R.id.registerButton)).perform(click())

        Intents.intended(IntentMatchers.hasComponent(Login::class.java.name))
        Intents.release()
    }
    // 1
    @Test
    fun testUsuarioVacio() {
        onView(withId(R.id.userName)).perform(clearText())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userName))
            .check(matches(hasErrorText("El nombre de usuario és obligatorio")))
    }
    // 2
    @Test
    fun testUsuarioCorto() {
        onView(withId(R.id.userName)).perform(clearText())
        onView(withId(R.id.userName)).perform(typeText("eu"), closeSoftKeyboard())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userName))
            .check(matches(hasErrorText("El nombre de usuario debe tener entre 5 y 20 carácteres alfanuméricos.")))
    }
    // 3
    @Test
    fun testUsuarioLargo() {
        onView(withId(R.id.userName)).perform(typeText("usuarioChristopherFlores"), closeSoftKeyboard())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userName))
            .check(matches(hasErrorText("El nombre de usuario debe tener entre 5 y 20 carácteres alfanuméricos")))
    }
    // 4
    @Test
    fun testUsuarioFormatoInvalido() {
        onView(withId(R.id.userName)).perform(typeText("usuariInvalid#123"), closeSoftKeyboard())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userName))
            .check(matches(hasErrorText("El nombre de usuario sólo puede contener carácteres alfanuméricos o “_”, “-” y “.”")))
    }
    // 5
    @Test
    fun testUsuarioInvalido() {
        onView(withId(R.id.userName)).perform(typeText("!usuariInvalid"), closeSoftKeyboard())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userName))
            .check(matches(hasErrorText("El nombre de usuario debe iniciar con una letra")))
    }
    // 6
    @Test           // investigar como hacer el accion de caso correcto, de inicio session
    fun testUsuarioValido() {
        onView(withId(R.id.userName)).perform(typeText("usuari_Valid"), closeSoftKeyboard())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userName))
            .check(matches(hasErrorText("")))
        // saber que fa lactivity, normalment esborrar error. "" quan es buida, set error ->
        /* match espreso contrari en cas de no error, si no te una clase sense cadena de ERROR,
        * la linea de codi que posa, comprova, realment mirar si propietat, */
    }
    // 1
    @Test
    fun testEmailVacio() {
        onView(withId(R.id.userEmail)).perform(clearText())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userEmail))
            .check(matches(hasErrorText("El correo electrónico és obligatorio")))
    }
    /*
    // 2
    @Test
    fun testEmailVacio() {
        onView(withId(R.id.userEmail)).perform(clearText())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userEmail))
            .check(matches(hasErrorText("El correo electrónico és obligatorio")))
    }
    // 3
    @Test
    fun testEmailVacio() {
        onView(withId(R.id.userEmail)).perform(clearText())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userEmail))
            .check(matches(hasErrorText("El correo electrónico és obligatorio")))
    }
    */
    @Test
    fun testPasswordVacio() {
        onView(withId(R.id.userPswwd)).perform(clearText())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userPswwd))
            .check(matches(hasErrorText("La contraseña és obligatoria")))
    }
    @Test
    fun testConfirmPasswordVacio() {
        onView(withId(R.id.userPswwdConfirm)).perform(clearText())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userPswwdConfirm))
            .check(matches(hasErrorText("La contraseña de confirmación és obligatoria")))
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.hometaste", appContext.packageName)
    }
}