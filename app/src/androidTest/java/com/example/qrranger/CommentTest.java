package com.example.qrranger;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
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
public class CommentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void commentTest() {
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
allOf(withId(R.id.LeaderboardRank4LL),
childAtPosition(
allOf(withId(R.id.LeaderboardLL1),
childAtPosition(
withId(R.id.LeaderboardFragment),
0)),
5),
isDisplayed()));
        linearLayout.perform(click());

        DataInteraction constraintLayout = onData(anything())
.inAdapterView(allOf(withId(R.id.OUQR_list_view),
childAtPosition(
withId(R.id.OtherUserLL9),
1)))
.atPosition(0);
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
allOf(withId(R.id.comment_add_button), withText("Add"),
childAtPosition(
childAtPosition(
withId(R.id.comments_ll),
0),
1),
isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatEditText = onView(
allOf(withId(R.id.comment_input),
childAtPosition(
childAtPosition(
withId(com.google.android.material.R.id.custom),
0),
0),
isDisplayed()));
        appCompatEditText.perform(replaceText("love this gem"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
allOf(withId(android.R.id.button1), withText("Submit"),
childAtPosition(
childAtPosition(
withId(com.google.android.material.R.id.buttonPanel),
0),
3)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction textView = onView(
allOf(withId(R.id.comment_text), withText("love this gem"),
withParent(withParent(withId(R.id.comments_list_view))),
isDisplayed()));
        textView.check(matches(withText("love this gem")));

        ViewInteraction materialButton4 = onView(
allOf(withId(R.id.comment_back_button), withText("Back"),
childAtPosition(
childAtPosition(
withId(R.id.comments_ll),
0),
0),
isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
allOf(withId(R.id.commentsOU), withText("Comments"),
childAtPosition(
allOf(withId(R.id.gem_layoutOU),
childAtPosition(
withId(android.R.id.content),
0)),
6),
isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textView2 = onView(
allOf(withId(R.id.comment_text), withText("love this gem"),
withParent(withParent(withId(R.id.comments_list_view))),
isDisplayed()));
        textView2.check(matches(withText("love this gem")));
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
