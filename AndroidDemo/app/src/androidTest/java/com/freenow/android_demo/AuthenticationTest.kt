package com.freenow.android_demo


import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import com.freenow.android_demo.activities.AuthenticationActivity

import org.junit.*

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthenticationTest {

    private var userName = "crazydog335"
    private var password = "venture"

    @get:Rule
    var activityRule = ActivityTestRule(AuthenticationActivity::class.java)


    @Test
    fun loginSuccessfully() {
        Espresso.onView(ViewMatchers.withId(R.id.edt_username)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.edt_username)).perform(replaceText(userName))
        onView(withId(R.id.edt_password)).perform(replaceText(password))
        onView(withId(R.id.btn_login)).perform(click())
    }
}