package com.freenow.android_demo


import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import androidx.test.uiautomator.*
import com.freenow.android_demo.activities.AuthenticationActivity
import com.freenow.android_demo.activities.MainActivity
import com.freenow.android_demo.utils.network.HttpClient
import com.freenow.misc.Constants.*
import com.freenow.utilities.Wait
import junit.framework.Assert

import org.junit.*

import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AuthenticationTest {

    //region define variables
    private var userName = "crazydog335"
    private var password = "venture"
    private var searchText = "sa"
    private var driverName = "Samantha Reed"
    private lateinit var device: UiDevice
    //endregion

    //region create rules for Activities
    @get:Rule
    var activityRule = ActivityTestRule(AuthenticationActivity::class.java)

    @get:Rule
    var mainActivityRule = ActivityTestRule(MainActivity::class.java)
    //endregion

    @Before
    fun login() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        //Check if the username text field has been displayed
        onView(withId(R.id.edt_username)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //Enter Username
        onView(withId(R.id.edt_username)).perform(typeText(userName))
        //Check if the password field has been displayed
        onView(withId(R.id.edt_password)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //Enter password
        onView(withId(R.id.edt_password)).perform(typeText(password))
        //Check if th the login has been displayed
        onView(withId(R.id.btn_login)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        //Click on Login
        onView(withId(R.id.btn_login)).perform(click())

        Thread.sleep(5000)
        //ًWait until the search bar has been displayed
        Wait.waitUntilViewLoaded(withId(R.id.textSearch))

        //check if the search text box has been displayed
        onView(withId(R.id.textSearch)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun searchDriver() {
        //Type the driver name in search text box
        onView(withId(R.id.textSearch)).perform(typeText(searchText));

        //Wait until the search results has been displayed in the container
        Wait.waitUntilViewLoaded(withId(R.id.searchContainer))

        //Click on the driver name
        onView(withText(driverName)).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        //Wait until the driver profile has been displayed

        Wait.waitUntilViewLoaded(withId(R.id.textViewDriverName))
        //Check the driver name profile has been opened correctly
        onView(withId(R.id.textViewDriverName)).check(ViewAssertions.matches(withText(driverName)));

        Wait.waitUntilViewLoaded(withId(R.id.fab))
        //Click on call button
        onView(withId(R.id.fab)).perform(click());

        //Wait until the dial package has been opened
        device.wait(
                Until.hasObject(By.pkg(DIALER_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT
        )

        //Get the phone number txt area
        val appItem: UiObject = device.findObject(
                UiSelector().resourceId("com.google.android.dialer:id/digits")
        )

        //get the phone number
        var phoneNumber = appItem.getText()


        //get the driver phone number
        var driverPhoneNumber = HttpClient.drivers.filter { s -> s.name == driverName }.first().phone.replace("-", "")

        //Compare the driver number with the number in the dial screen
        Assert.assertEquals(phoneNumber, driverPhoneNumber)
    }

    @After
    fun goToHomeScreen() {
        //Go to Home Screen
        device.pressHome()
    }
}