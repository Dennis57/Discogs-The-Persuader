package com.example.thepersuader.releaseDetail

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.thepersuader.R
import com.example.thepersuader.adapter.TracklistAdapter
import com.example.thepersuader.util.RecyclerViewMatcher
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class TrackListFragmentTest {

  @Test
  fun checkFirstTrackList_Valid_Success() {
    val bundle = Bundle()

    launchFragmentInContainer<TrackListFragment>(bundle, R.style.Theme_ThePersuader)

    onView(withId(R.id.rv_tracklist))
      .check(matches(RecyclerViewMatcher.atPosition(0, isDisplayed())))
      .check(matches(RecyclerViewMatcher.atPosition(0, hasDescendant(withText("TrackList 1")))))
  }

//  @Test
//  fun checkLastTrackList_Valid_Success() {
//    val bundle = Bundle()
//
//    launchFragmentInContainer<TrackListFragment>(bundle, R.style.Theme_ThePersuader)
//
//    onView(withId(R.id.rv_tracklist))
//      .perform(RecyclerViewActions.scrollToPosition<TracklistAdapter.TracklistViewHolder>(17))
//      .check(matches(RecyclerViewMatcher.atPosition(17, isDisplayed())))
//      .check(matches(RecyclerViewMatcher.atPosition(17, hasDescendant(withText("TrackList 18")))))
//  }
}