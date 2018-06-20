package com.tbuonomo.morphbottomnavigationsample

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MorphBottomNavigationTest {

  @get:Rule
  val activityRule = ActivityTestRule(MainActivity::class.java)

  @Test
  fun visibilityTest() {
    onView(withId(R.id.bottomNavigationView)).perform(click())
  }
}
