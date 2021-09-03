package com.example.thepersuader.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.thepersuader.R
import com.example.thepersuader.adapter.ReleaseAdapter
import org.hamcrest.core.AllOf
import org.hamcrest.core.Is
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainActivityTest {

  @Test
  fun checkArtistDetail() {
    ActivityScenario.launch(MainActivity::class.java)

    onView(withId(R.id.tv_artist_name))
      .check(matches(isDisplayed()))
      .check(matches(withText("The Persuader")))

    onView(withId(R.id.tv_aliases))
      .check(matches(isDisplayed()))
      .check(matches(withText("Persuader Aliases")))

    onView(withId(R.id.tv_real_name))
      .check(matches(isDisplayed()))
      .check(matches(withText("Real Persuader")))

    Thread.sleep(3000)
  }

  @Test
  fun checkReleaseDetail() {
    ActivityScenario.launch(MainActivity::class.java)
    onView(withId(R.id.rv_releases))
      .perform(actionOnItemAtPosition<ReleaseAdapter.ReleaseViewHolder>(0, click()))

    onView(withId(R.id.tv_title))
      .check(matches(withText("Album Release 1")))
  }

//  @Test
//  fun checkRelease() {
//    Thread.sleep(1500)
//
//    onView(AllOf.allOf(withText("Cosmic Isolation EP"))).check(matches(isDisplayed()))
//  }
//
//  @Test
//  fun checkReleaseDetail() {
//    Thread.sleep(1500)
//
//    onView(AllOf.allOf(withText("Cosmic Isolation EP"))).perform(click())
//    Thread.sleep(500)
//    onView(withId(R.id.tv_title)).check(matches(withText("Cosmic Isolation EP")))
//    onView(withId(R.id.tv_release_year)).check(matches(withText("2019")))
//  }


  @Test
  fun useAppContext() {
    // Context of the app under test.
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    Assert.assertEquals("com.example.thepersuader", appContext.packageName)
  }

}