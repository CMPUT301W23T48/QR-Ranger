package com.example.qrranger;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LeaderboardTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void leaderboardTest() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.stat), withContentDescription("Leaderboard"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigator),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.LeaderboardRank1LL),
                        childAtPosition(
                                allOf(withId(R.id.LeaderboardLL1),
                                        childAtPosition(
                                                withId(R.id.LeaderboardFragment),
                                                0)),
                                2),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.OUBackButton), withText("Back"),
                        childAtPosition(
                                allOf(withId(R.id.OtherUserLL3),
                                        childAtPosition(
                                                withId(R.id.OtherUserLL2),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction linearLayout2 = onView(
                allOf(withId(R.id.LeaderboardRank2LL),
                        childAtPosition(
                                allOf(withId(R.id.LeaderboardLL1),
                                        childAtPosition(
                                                withId(R.id.LeaderboardFragment),
                                                0)),
                                3),
                        isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.OUBackButton), withText("Back"),
                        childAtPosition(
                                allOf(withId(R.id.OtherUserLL3),
                                        childAtPosition(
                                                withId(R.id.OtherUserLL2),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction linearLayout3 = onView(
                allOf(withId(R.id.LeaderboardRank3LL),
                        childAtPosition(
                                allOf(withId(R.id.LeaderboardLL1),
                                        childAtPosition(
                                                withId(R.id.LeaderboardFragment),
                                                0)),
                                4),
                        isDisplayed()));
        linearLayout3.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.OUBackButton), withText("Back"),
                        childAtPosition(
                                allOf(withId(R.id.OtherUserLL3),
                                        childAtPosition(
                                                withId(R.id.OtherUserLL2),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
