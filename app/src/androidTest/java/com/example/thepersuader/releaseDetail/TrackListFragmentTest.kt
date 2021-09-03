package com.example.thepersuader.releaseDetail

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.thepersuader.R
import org.hamcrest.core.AllOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class TrackListFragmentTest {

  @Test
  fun checkTrackList_Valid_Success() {
    val bundle = Bundle()

    launchFragmentInContainer<TrackListFragment>(bundle, R.style.Theme_ThePersuader)

    Thread.sleep(1500)
    onView(AllOf.allOf(withText("TrackList 1"))).check(matches(isDisplayed()))
    Thread.sleep(1500)
  }
}