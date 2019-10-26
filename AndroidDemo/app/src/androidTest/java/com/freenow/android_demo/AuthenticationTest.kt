package com.freenow.android_demo


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import com.freenow.android_demo.activities.AuthenticationActivity
import com.freenow.android_demo.activities.MainActivity

import org.junit.*

import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
class AuthenticationTest {

    private var userName = "crazydog335"
    private var password = "venture"
    private var searchText = "sa"
    private var driverName = "Samantha Reed"

    @get:Rule
    var activityRule = ActivityTestRule(AuthenticationActivity::class.java)

    @get:Rule
    var mainActivityRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun loginSuccessfully() {
        //Check if the username text field has been displayed
        onView(ViewMatchers.withId(R.id.edt_username)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //Enter Username
        onView(withId(R.id.edt_username)).perform(typeText(userName))
        //Check if the password field has been displayed
        onView(ViewMatchers.withId(R.id.edt_password)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //Enter password
        onView(withId(R.id.edt_password)).perform(typeText(password))
        //Check if th the login has been displayed
        onView(ViewMatchers.withId(R.id.btn_login)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //Click on Login
        onView(withId(R.id.btn_login)).perform(click())

        Thread.sleep(3000)
        onView(ViewMatchers.withId(R.id.textSearch)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.textSearch)).perform(typeText(searchText));
        Thread.sleep(3000)
        onView(withText(driverName)).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        Thread.sleep(3000)
        onView(withId(R.id.textViewDriverName)).check(ViewAssertions.matches(withText(driverName)));
        Thread.sleep(3000)
        onView(withId(R.id.fab)).perform(click());
    }
}