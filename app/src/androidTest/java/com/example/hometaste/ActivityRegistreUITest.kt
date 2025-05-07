package com.example.hometaste

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
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

    @Test
    fun testEmailVacio() {
        onView(withId(R.id.userEmail)).perform(clearText())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userEmail))
            .check(matches(hasErrorText("El correo electrónico és obligatorio")))
    }

    @Test
    fun testEmailSinArroba() {
        onView(withId(R.id.userEmail)).perform(clearText())
        onView(withId(R.id.userEmail)).perform(typeText("usuario.gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.registerButton)).perform(click())
        onView(withId(R.id.userEmail)).check(matches(hasErrorText("Introduce un correo electrónico válido, que contiene un “@”.")))
    }
}