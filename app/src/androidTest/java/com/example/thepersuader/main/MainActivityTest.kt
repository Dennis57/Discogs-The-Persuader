package com.example.thepersuader.main

import android.content.Intent
import androidx.test.InstrumentationRegistry.getContext
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.thepersuader.R
import com.example.thepersuader.repository.ArtistRepositoryInterface
import com.example.thepersuader.repository.FakeArtistRepository
import org.hamcrest.core.AllOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainActivityTest {

  @Test
  fun checkArtistDetail() {
    val scenario = ActivityScenario.launch(MainActivity::class.java)
    Thread.sleep(1500)

    onView(withId(R.id.tv_artist_name)).check(matches(isDisplayed()))
    onView(withId(R.id.tv_artist_name)).check(matches(withText("The Persuader")))
    onView(withId(R.id.tv_aliases)).check(matches(isDisplayed()))
    onView(withId(R.id.tv_real_name)).check(matches(isDisplayed()))
    onView(withId(R.id.tv_real_name)).check(matches(withText("Jesper Dahlbäck")))

    Thread.sleep(1500)
  }

  @Test
  fun checkRelease() {
    Thread.sleep(1500)

    onView(AllOf.allOf(withText("Cosmic Isolation EP"))).check(matches(isDisplayed()))
  }

  @Test
  fun checkReleaseDetail() {
    Thread.sleep(1500)

    onView(AllOf.allOf(withText("Cosmic Isolation EP"))).perform(click())
    Thread.sleep(500)
    onView(withId(R.id.tv_title)).check(matches(withText("Cosmic Isolation EP")))
    onView(withId(R.id.tv_release_year)).check(matches(withText("2019")))
  }


  @Test
  fun useAppContext() {
    // Context of the app under test.
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    Assert.assertEquals("com.example.thepersuader", appContext.packageName)
  }

}