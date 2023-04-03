package com.example.qrranger;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
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
public class LeaderBoardTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Tests leaderboard, and other user profile views
     */
    @Test
    public void leaderBoardTest() {
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
        
        DataInteraction constraintLayout = onData(anything())
.inAdapterView(allOf(withId(R.id.OUQR_list_view),
childAtPosition(
withId(R.id.OtherUserLL9),
1)))
.atPosition(1);
        constraintLayout.perform(click());
        
        ViewInteraction materialButton = onView(
allOf(withId(R.id.commentsOU), withText("Comments"),
childAtPosition(
allOf(withId(R.id.gem_layoutOU),
childAtPosition(
withId(android.R.id.content),
0)),
6),
isDisplayed()));
        materialButton.perform(click());
        
        ViewInteraction materialButton2 = onView(
allOf(withId(R.id.comment_back_button), withText("Back"),
childAtPosition(
childAtPosition(
withId(R.id.comments_ll),
0),
0),
isDisplayed()));
        materialButton2.perform(click());
        
        ViewInteraction materialButton3 = onView(
allOf(withId(R.id.users_that_scannedOU), withText("Users"),
childAtPosition(
allOf(withId(R.id.gem_layoutOU),
childAtPosition(
withId(android.R.id.content),
0)),
8),
isDisplayed()));
        materialButton3.perform(click());
        
        ViewInteraction materialButton4 = onView(
allOf(withId(R.id.users_that_scanned_back_button), withText("Back"),
childAtPosition(
childAtPosition(
withId(R.id.users_that_scanned_ll),
0),
1),
isDisplayed()));
        materialButton4.perform(click());
        
        ViewInteraction materialButton5 = onView(
allOf(withId(R.id.gem_view_back_buttonOU), withText("Back"),
childAtPosition(
allOf(withId(R.id.gem_layoutOU),
childAtPosition(
withId(android.R.id.content),
0)),
9),
isDisplayed()));
        materialButton5.perform(click());
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
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
