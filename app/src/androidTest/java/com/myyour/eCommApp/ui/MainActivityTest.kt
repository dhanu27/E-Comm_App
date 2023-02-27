package com.myyour.eCommApp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Rule
import org.junit.Test
import com.myyour.eCommApp.R

class MainActivityTest {
    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun `test_bottomNavigationButton_expectedGridFragment`() {
        onView(withId(R.id.linear_fragment)).check(matches((isCompletelyDisplayed())))
        onView(withId(R.id.grid_view_fragment)).perform(click())
        onView(withId(R.id.grid_fragment)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun `test_swipeLeft_expectedGridFragment`() {
        onView(withId(R.id.linear_fragment)).perform(swipeLeft())
        onView(withId(R.id.grid_fragment)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.grid_view_fragment)).check(matches(isSelected()))
        onView(withId(R.id.linear_view_fragment)).check(matches(isNotSelected()))
    }


    @Test
    fun `test_swipeRightLinearFragment_expectedNoChange`() {
        onView(withId(R.id.linear_fragment)).perform(swipeRight())
        onView(withId(R.id.linear_fragment)).check(matches(isCompletelyDisplayed()))
    }

}
